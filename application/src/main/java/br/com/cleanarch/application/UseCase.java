package br.com.cleanarch.application;

public abstract class UseCase<INPUT, OUTPUT> {

    public abstract OUTPUT execute(INPUT input);

}
