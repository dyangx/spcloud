package jnuitTest;

import com.cloud.user.vo.User;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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

    @org.junit.Test
    public void test1(){
        ExpressionParser exp = new SpelExpressionParser();
        User u = new User();
        u.setId("1");
        u.setName("马杀");
        Expression ep = exp.parseExpression("我的id是#{id}，name是#{name},'#name',#{uu.name}",new TemplateParserContext());
        EvaluationContext ctx=new StandardEvaluationContext();
        ctx.setVariable("uu",u);
        System.out.println(ep.getValue());
//        System.out.println(exp.parseExpression("#{id}，name是#{name},'#name',#{uu.name}").getValue(ctx));
    }

    @org.junit.Test
    public void test2(){
        User u = new User();
        u.setId("1");
        u.setName("马杀");
        String greetingExp = "Hello, #{ #user }, #{ #u.name }";
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", "Gangyou");
        context.setVariable("u", u);

        Expression expression = parser.parseExpression(greetingExp,
                new TemplateParserContext());
        System.out.println(expression.getValue(context, String.class));
    }


}
