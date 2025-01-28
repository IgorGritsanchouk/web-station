package com.mm.ai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
//import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class BeanConfig {

    private final Logger logger = LoggerFactory.getLogger(BeanConfig.class);

    private final VectorStore vectorStore;

    public BeanConfig(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    @Value("${local.pgvector.load}")
    private String pgVectorLoad;

    @Bean
    public ChatClient openAIChatClient(OpenAiChatModel chatModel) {

        logger.info("OpenAIChatClient / MessageChatMemoryAdvisor - model: " + chatModel.getDefaultOptions().getModel());
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        ChatClient chatClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
        logger.info("OpenAI chat client initialized ok. ");

        return chatClient;
    }

    @Bean
    public ChatClient regOpenAIChatClient(OpenAiChatModel chatModel) {  //, VectorStore vectorStore) {

        logger.info("RegOpenAIChatClient  - model: " + chatModel.getDefaultOptions().getModel());
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        //ChatClient chatClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()), new VectorStoreChatMemoryAdvisor(vectorStore))
        //        .build();

        ChatClient chatClient = builder.defaultAdvisors(new SimpleLoggerAdvisor(), new VectorStoreChatMemoryAdvisor(vectorStore))
                .build();
        //ChatClient chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
        //        .build();
        logger.info("RegOpenAIChatClient initialized ok: ");

        return chatClient;
    }

    @Bean
    ApplicationRunner pgVectorPdfLoader(
            JdbcTemplate jdbcTemplate,
            //VectorStore vectorStore,
            @Value("${documents.directory.path}") String directoryPath
    ) {

        if(!pgVectorLoad.equals("true")){
            logger.info("PGVector database has already been populated.");
            return args -> {};
        }

        ClassPathResource classPathResource = new ClassPathResource(directoryPath);
        //String currentPath = new java.io.File(".").getCanonicalPath();

        return args -> {
            Path directory = Paths.get(classPathResource.getFile().getAbsolutePath());
            //Path directory = Paths.get(directoryPath);
            logger.info("PDF directory: {}", directoryPath);
            try (var paths = Files.list(directory)) {
                paths.filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {

                                Integer count =0;
                                if(!pgVectorLoad.equals("true")) {
                                    String fileName = path.getFileName().toString();
                                    String sql = "SELECT count(*) FROM vector_store WHERE metadata->>'file_name' = ?";
                                    count = jdbcTemplate.queryForObject(sql, new Object[]{fileName}, Integer.class);

                                    if (count > 0) {
                                        logger.info("PDF : {} is already loaded in pgvector db.", fileName);
                                    }
                                }
                                if (count == 0 && pgVectorLoad.equals("true")) {
                                    Resource resource = new UrlResource(path.toUri());
                                    logger.info("Reading PDF: {}", resource.getFilename());
                                    int startTime = (int) System.currentTimeMillis();
                                    PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource,
                                            PdfDocumentReaderConfig.builder()
                                                    .withPageTopMargin(0)
                                                    .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                                            .withNumberOfTopTextLinesToDelete(0)
                                                            .build())
                                                    .withPagesPerDocument(1)
                                                    .build());

                                    var textSplitter = new TokenTextSplitter();
                                    var docs = textSplitter.apply(pdfReader.get());
                                    logger.info("Loading PDF: {}", resource.getFilename());
                                    vectorStore.accept(docs);
                                    logger.info("Loaded PDF complete: {}", resource.getFilename());
                                    int endTime = (int) System.currentTimeMillis();
                                    logger.info("Time taken to load PDF: {} ms", endTime - startTime);
                                }

                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }

                        });
            }

        };
    }

//   removed ollama as it is producing exceptions in RAG pgvector db
//    @Bean
//    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
//
//        logger.info("OllamaChatClient / MessageChatMemoryAdvisor - model: " + chatModel.getDefaultOptions().getModel());
//        ChatClient.Builder builder = ChatClient.builder(chatModel);
//
//        ChatClient chatClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
//                .build();
//        logger.info("OllamaChatClient chat client initialized ok. ");
//
//        return chatClient;
//    }

}
