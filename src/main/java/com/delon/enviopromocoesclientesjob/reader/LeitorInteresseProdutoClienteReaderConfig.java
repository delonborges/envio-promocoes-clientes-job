package com.delon.enviopromocoesclientesjob.reader;

import com.delon.enviopromocoesclientesjob.domain.Cliente;
import com.delon.enviopromocoesclientesjob.domain.InteresseProdutoCliente;
import com.delon.enviopromocoesclientesjob.domain.Produto;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
public class LeitorInteresseProdutoClienteReaderConfig {

    @Bean
    public JdbcCursorItemReader<InteresseProdutoCliente> leitorInteresseProdutoClienteReader(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>().name("leitorInteresseProdutoClienteReader")
                                                                         .dataSource(dataSource)
                                                                         .sql("""
                                                                              select *
                                                                              from interesse_produto_cliente
                                                                                       join cliente on cliente = cliente.id
                                                                                       join produto on produto = produto.id;
                                                                              """)
                                                                         .rowMapper(rowMapper())
                                                                         .build();
    }

    private RowMapper<InteresseProdutoCliente> rowMapper() {

        return (rs, rowNum) -> {
            var cliente = Cliente.builder().id(rs.getInt("id")).nome(rs.getString("nome")).email(rs.getString("email")).build();
            var produto = Produto.builder().id(rs.getInt(6)).nome(rs.getString(7)).descricao(rs.getString("descricao")).preco(rs.getDouble("preco")).build();
            return InteresseProdutoCliente.builder().cliente(cliente).produto(produto).build();
        };
    }
}
