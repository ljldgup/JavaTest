package FunctionalProgram;

@FunctionalInterface
interface Action<T> {
    public void execute(T t);
}