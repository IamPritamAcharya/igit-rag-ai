# IGIT RAG AI

A Retrieval-Augmented Generation (RAG) system built using Spring Boot, PostgreSQL (pgvector), and Ollama (llama3) to answer queries based on data from IGIT Sarang.

---

## Overview

This project creates a domain-specific AI assistant for IGIT Sarang by:

* Scraping notices and documents from the official website
* Extracting text from PDFs (including scanned documents using OCR)
* Converting content into embeddings
* Storing embeddings in PostgreSQL with pgvector
* Performing semantic + keyword-based retrieval
* Generating answers using a local LLM (llama3 via Ollama)

---

## Architecture

```
Scraper (Jsoup)
        ↓
PDF Extraction (PDFBox + Tesseract OCR)
        ↓
Chunking
        ↓
Embeddings (Ollama)
        ↓
PostgreSQL + pgvector
        ↓
Search (Hybrid: vector + keyword)
        ↓
LLM (llama3 via Ollama)
        ↓
Spring Boot API
```

---

## Features

* Web scraping with pagination support
* OCR support for scanned PDFs
* Document chunking with overlap
* Local embeddings using Ollama
* Vector search using pgvector
* Hybrid retrieval (semantic + keyword)
* Async ingestion pipeline
* Duplicate detection
* Question answering using llama3

---

## Tech Stack

* Backend: Spring Boot (Java)
* Database: PostgreSQL + pgvector
* LLM: Ollama (llama3)
* Embeddings: Ollama (nomic-embed-text)
* OCR: Tesseract (via Tess4J)
* PDF Processing: Apache PDFBox
* Scraping: Jsoup

---

## Setup

### 1. Clone the repository

```
git clone https://github.com/your-username/igit-rag-ai.git
cd igit-rag-ai
```

---

### 2. Configure PostgreSQL

* Install PostgreSQL
* Install pgvector extension

```
CREATE EXTENSION vector;
```

Update `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

### 3. Install Ollama

```
curl -fsSL https://ollama.com/install.sh | sh
```

Pull required models:

```
ollama pull llama3
ollama pull nomic-embed-text
```

Start Ollama:

```
sudo systemctl start ollama
```

---

### 4. Install Tesseract (for OCR)

```
sudo apt install tesseract-ocr
sudo apt install tesseract-ocr-eng
```

Ensure path is set in code:

```
/usr/share/tesseract-ocr/5/tessdata
```

---

### 5. Run the application

```
mvn spring-boot:run
```

Server runs on:

```
http://localhost:8082
```

---

## API Endpoints

### Ingest a document

```
POST /api/ingest
```

Params:

* `url`
* `title`
* `category`

---

### Scrape notices

```
POST /api/scrape/notices?url=https://igitsarang.ac.in/notice/2026
```

---

### Ask a question

```
GET /api/ask?question=your_query
```

Example:

```
/api/ask?question=when is alumni connect series 2026?
```

---

### Extract PDF text (testing)

```
GET /api/pdf/extract?url=PDF_URL
```

---

## How It Works

1. Scraper collects document links from IGIT website
2. PDFs are downloaded and processed
3. OCR is applied if text is not selectable
4. Content is chunked and embedded
5. Embeddings are stored in pgvector
6. Query is converted to embedding
7. Relevant chunks are retrieved
8. LLM generates final answer

---

## Known Limitations

* OCR may introduce noise in extracted text
* Local LLM (llama3) can be slow on CPU
* Retrieval quality depends on chunk quality
* No frontend UI (backend only)

---

## Future Improvements

* Better OCR post-processing
* Source citation in responses
* Streaming responses
* UI (Flutter) integration
* Switch to faster LLM APIs (optional)
* Background job queue (Kafka/Redis)

---

## License

This project is for educational and experimental purposes.

---

## Author

Developed as a learning project to understand RAG systems, semantic search, and local LLM integration.
