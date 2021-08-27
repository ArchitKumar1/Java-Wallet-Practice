package practice.oops;

import lombok.AllArgsConstructor;

public class callable {
    public static void main(String[] args) {

        Runner<Code> runner = new Code<Code>("a = 2");
        runner.run();

        Printer<Code> printer = new Code<Code>("a = 2");
        printer.print();
    }

    public interface Runner<T> {
        void run();
    }

    public interface Printer<T> {
        void print();
    }

    public static class Run<T> implements Runner<T> {
        @Override
        public void run() {
            System.out.println("running ");
        }
    }

    @AllArgsConstructor
    public static class Code<T> implements Runner<T>, Printer<T>{
        String code;
        @Override
        public void run() {
            System.out.println("running" + code);
        }
        @Override
        public void print() {
            System.out.println("printing" + code);
        }
    }
}
