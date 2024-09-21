CREATE TABLE diretorio (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    diretorio_id BIGINT,
    CONSTRAINT fk_diretorio_pai
        FOREIGN KEY (diretorio_id) REFERENCES diretorio(id)
        ON DELETE CASCADE
);

CREATE TABLE arquivo (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    diretorio_id BIGINT,
    CONSTRAINT fk_arquivo_diretorio
        FOREIGN KEY (diretorio_id) REFERENCES diretorio(id)
        ON DELETE CASCADE
);
