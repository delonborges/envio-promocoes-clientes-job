package com.delon.enviopromocoesclientesjob.processor;

import com.delon.enviopromocoesclientesjob.domain.InteresseProdutoCliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.text.NumberFormat;

@Component
public class ProcessadorInteresseProdutoClienteProcessorConfig implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> {

    private static final String TWO_LINE_SEPARATORS = System.lineSeparator().repeat(2);

    @Override
    public SimpleMailMessage process(InteresseProdutoCliente interesseProdutoCliente) throws InterruptedException {
        var email = new SimpleMailMessage();
        email.setFrom("delon@no-reply.com");
        email.setTo(interesseProdutoCliente.cliente().email());
        email.setSubject("Promoção Imperdível!");
        email.setText(geraTextoPromocao(interesseProdutoCliente));
        Thread.sleep(2000);
        return email;
    }

    private String geraTextoPromocao(InteresseProdutoCliente interesseProdutoCliente) {
        var writer = new StringWriter();
        writer.append(String.format("Olá, %s!", interesseProdutoCliente.cliente().nome()));
        writer.append(TWO_LINE_SEPARATORS);
        writer.append("Essa promoção pode ser do seu interesse:");
        writer.append(TWO_LINE_SEPARATORS);
        writer.append(String.format("%s - %s", interesseProdutoCliente.produto().nome(), interesseProdutoCliente.produto().descricao()));
        writer.append(TWO_LINE_SEPARATORS);
        writer.append(String.format("Por apenas: %s!", NumberFormat.getCurrencyInstance().format(interesseProdutoCliente.produto().preco())));
        return writer.toString();
    }
}
