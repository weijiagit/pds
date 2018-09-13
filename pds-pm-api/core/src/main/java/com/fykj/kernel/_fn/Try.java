package com.fykj.kernel._fn;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Try {
	
//	public static <T,R> Function<T, R> apply(UncheckedFunction<T, R> function){
//		Objects.requireNonNull(function);
//		return t->{
//			try {
//				return function.apply(t);
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		};
//	}
//	
//	
//	@FunctionalInterface
//    public static interface UncheckedFunction<T, R> {
//        R apply(T t) throws Exception;
//    }

	
	
	
	public static <T,U> BiConsumer<T,U> accept(UncheckedMapFunction<T, U> function){
		Objects.requireNonNull(function);
        return (l, r) -> {
        	try {
				function.accept(l, r);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
        };
	}

	public static <T,R> Consumer<T> apply(UncheckedComsumerFunction<? super T> function){
		Objects.requireNonNull(function);
		return t->{
			try {
				function.apply(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
	
	
	@FunctionalInterface
    public static interface UncheckedComsumerFunction<T> {
        void apply(T t) throws Exception;
    }
	
	@FunctionalInterface
	public static interface UncheckedMapFunction<T, U>{
		 /**
	     * Performs this operation on the given arguments.
	     *
	     * @param t the first input argument
	     * @param u the second input argument
	     */
	    void accept(T t, U u) throws Exception;
	    
	}
	
	
	
	
	
	
	
	
	
	
	
}
