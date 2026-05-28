package com.api.list.config;

import com.api.list.entity.Item;
import com.api.list.entity.Supplier;
import com.api.list.repository.ItemRepository;
import com.api.list.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final SupplierRepository supplierRepository;

    private static final List<String> NOMES_PRODUTOS = Arrays.asList(
            "Notebook", "Smartphone", "Monitor", "Teclado", "Mouse",
            "Webcam", "Headset", "Impressora", "Tablet", "Smartwatch",
            "Carregador", "Cabo USB", "HD Externo", "SSD", "Memória RAM",
            "Placa de Vídeo", "Processador", "Placa Mãe", "Fonte", "Gabinete",
            "Roteador", "Switch", "Cabo de Rede", "Adaptador", "Mousepad"
    );

    private static final List<String> FORNECEDORES = Arrays.asList(
            "TechDistribuidora", "InfoComércio", "Digital Supplier",
            "EletroPeças", "MegaInfo", "GlobalTech", "SmartTrade",
            "NextGen Supplies", "Prime Components", "UltraDistribuidora"
    );

    private static final List<String> DESCRICOES = Arrays.asList(
            "Produto de alta qualidade", "Ideal para uso profissional",
            "Ótimo custo-benefício", "Última versão do mercado",
            "Garantia estendida de 1 ano", "Produto certificado",
            "Design ergonômico e moderno", "Tecnologia de ponta",
            "Economia de energia", "Alta durabilidade"
    );

    @Override
    public void run(String... args) {

        if (supplierRepository.count() == 0) {
            createSuppliers();
        }

        if (itemRepository.count() == 0) {
            createItems();
        }
    }

    private void createSuppliers() {

        for (int i = 0; i < FORNECEDORES.size(); i++) {

            String nomeFornecedor = FORNECEDORES.get(i);

            Supplier supplier = Supplier.builder()
                    .name(nomeFornecedor)
                    .description("Fornecedor especializado em tecnologia e eletrônicos")
                    .cnpj(generateCnpj(i))
                    .email(nomeFornecedor.toLowerCase().replace(" ", "") + "@email.com")
                    .phone("(11) 99999-00" + i)
                    .build();

            supplierRepository.save(supplier);
        }
    }

    private void createItems() {

        Random random = new Random();

        List<Supplier> suppliers = supplierRepository.findAll();

        for (int i = 0; i < 30; i++) {

            String nome = NOMES_PRODUTOS.get(random.nextInt(NOMES_PRODUTOS.size()));
            String descricao = DESCRICOES.get(random.nextInt(DESCRICOES.size()));
            Integer quantidade = random.nextInt(100) + 1;

            BigDecimal buyPrice = BigDecimal.valueOf(random.nextDouble() * 1990 + 10)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal sellPrice = buyPrice
                    .multiply(BigDecimal.valueOf(1.3 + random.nextDouble() * 0.5))
                    .setScale(2, RoundingMode.HALF_UP);

            Supplier supplier = suppliers.get(random.nextInt(suppliers.size()));

            Item item = Item.builder()
                    .name(nome + (i + 1))
                    .description(descricao + " - SKU: " + (10000 + i))
                    .quantity(quantidade)
                    .buyPrice(buyPrice)
                    .sellPrice(sellPrice)
                    .supplier(supplier)
                    .build();

            itemRepository.save(item);
        }
    }

    private String generateCnpj(int index) {
        return String.format("00.000.000/000%d-%02d", index, index);
    }
}