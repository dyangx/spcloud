package jnuitTest;

import reactor.core.publisher.Flux;

/**
 * @author: yangjie
 * @date: Created in 2020/3/19 14:40
 */
public class Test {

    @org.junit.Test
    public void test(){
        Flux.range(1, 10).map(x -> x*x).subscribe(System.out::print);
//        Util.say();
    }
}
