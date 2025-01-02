package br.com.cleanarch.application;

public abstract class UnitUseCase<INPUT> {
    public abstract void execute(INPUT input);
}
