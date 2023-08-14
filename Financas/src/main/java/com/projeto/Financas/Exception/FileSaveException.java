package com.projeto.Financas.Exception;

public class FileSaveException extends Throwable {
    public FileSaveException(String erroAoSalvarOArquivo) {
            super(erroAoSalvarOArquivo);
    }
}
