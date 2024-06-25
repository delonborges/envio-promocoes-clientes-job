package com.delon.enviopromocoesclientesjob.processor;

import com.delon.enviopromocoesclientesjob.domain.InteresseProdutoCliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.text.NumberFormat;

@Component
public class ProcessadorInteresseProdutoClienteProcessorConfig implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> {

    @Override
    public SimpleMailMessage process(InteresseProdutoCliente interesseProdutoCliente) {
        var email = new SimpleMailMessage();
        email.setFrom("delon@no-reply.com");
        email.setTo(interesseProdutoCliente.cliente().email());
        email.setSubject("Promoção Imperdível!");
        email.setText(geraTextoPromocao(interesseProdutoCliente));
        return email;
    }

    private String geraTextoPromocao(InteresseProdutoCliente interesseProdutoCliente) {
        var writer = new StringWriter();
        writer.append(String.format("Olá, %s!%n%n", interesseProdutoCliente.cliente().nome()));
        writer.append("Essa promoção pode ser do seu interesse:%n%n");
        writer.append(String.format("%s - %s%n%n", interesseProdutoCliente.produto().nome(), interesseProdutoCliente.produto().descricao()));
        writer.append(String.format("Por apenas: %s!", NumberFormat.getCurrencyInstance().format(interesseProdutoCliente.produto().preco())));
        return writer.toString();
    }
}
