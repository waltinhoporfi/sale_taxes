import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ProdutosApplication {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        ArrayList<Produtos> produtos = new ArrayList<>();
        int i = 0;
        do{
            System.out.println("Escolha o codigo do produto desejado: ");
            System.out.println("1 - Livro:                        12,49\n2 - CD de musica:                 14,99" +
                    "\n3 - Barra de chocolate:           0,85");
            System.out.println("4 - Caixa de chocolate importada: 10,00\n5 - Frasco de perfume importado:  47,50");
            System.out.println("6 - Frasco de perfume2 importado: 27,99\n7 - Frasco de perfume:            18,99");
            System.out.println("8 - Comprimidos p/ dor de cabeça: 9,75\n9 - Caixa chocolate2 importada:   11,25");
            int codProd = scan.nextInt();

            Produtos item = new Produtos();
            item = totalizacao(item, codProd);
            verificacaoImposto(item);
            produtos.add(i, item);
            i = i + 1;
            System.out.println("Deseja adicionar mais algum produto?\n1 - Sim\n2 - Não\n");
        }while (scan.nextInt()==1);

        reciboEmTela(produtos);

    }

    private static void reciboEmTela(ArrayList<Produtos> produtos) {
        int i = 0;
        BigDecimal total = new BigDecimal(0);
        BigDecimal totalImpostos = new BigDecimal(0);
        final Locale LOCAL = new Locale("pt","BR");
        DecimalFormat f = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(LOCAL));

        System.out.println("Produtos:\n");
        while(i < produtos.size()){
            if(produtos.get(i).getImposto() == null){
                System.out.println(produtos.get(i).getQuantidade()
                        +" "+produtos.get(i).getDescricao()+": "
                        + f.format(produtos.get(i).getPreco()));

                totalImpostos = totalImpostos.add(new BigDecimal(0));
                total = total.add((produtos.get(i).getPreco()));
            }else {
                System.out.println(produtos.get(i).getQuantidade()
                        + " " + produtos.get(i).getDescricao() + ": "
                        + f.format(produtos.get(i).getPreco()));

                totalImpostos = totalImpostos.add(produtos.get(i).getImposto());
                total = total.add(produtos.get(i).getPreco());
            }

            i = i + 1;
        }
        System.out.println("Impostos sobre vendas: "+ f.format(totalImpostos));
        System.out.println("Total: " + f.format(total));
    }

    private static void verificacaoImposto(Produtos produto) {

        if(produto.getCategoria() == 1 || produto.getCategoria() == 3 || produto.getCategoria() == 8){
            if(produto.isImportado()){
                produto.setImposto(produto.getPreco().multiply(new BigDecimal(0.05).setScale(2, RoundingMode.HALF_UP)));
                BigDecimal result = new BigDecimal(String.valueOf(produto.getImposto()
                        .remainder(new BigDecimal(0.05).setScale(2, RoundingMode.HALF_EVEN))));
                if(result.compareTo(new BigDecimal(0.00)) != 0){
                    BigDecimal retorno = arredondar(produto.getImposto());
                    produto.setImposto(retorno);
                    produto.setPreco(produto.getPreco().add(produto.getImposto()));
                    return;
                }
                produto.setPreco(produto.getPreco().add(produto.getImposto()));
            }else {
                return;
            }

        }else {
            if(produto.isImportado()){
                produto.setImposto(produto.getPreco()
                        .multiply(new BigDecimal(0.15).setScale(2, RoundingMode.HALF_EVEN)));
                BigDecimal result = new BigDecimal(String.valueOf(produto.getImposto()
                        .remainder(new BigDecimal(0.05).setScale(2, RoundingMode.HALF_EVEN))));
                if(result.compareTo(new BigDecimal(0.00)) != 0){
                    BigDecimal retorno = arredondar(produto.getImposto());
                    produto.setImposto(retorno);
                    produto.setPreco(produto.getPreco().add(produto.getImposto()));
                }
            }else {
                produto.setImposto(produto.getPreco().multiply(new BigDecimal(0.10)
                        .setScale(2, RoundingMode.HALF_EVEN)));
                BigDecimal result = new BigDecimal(String.valueOf(produto.getImposto()
                        .remainder(new BigDecimal(0.05).setScale(2, RoundingMode.HALF_EVEN))));
                if(result.compareTo(new BigDecimal(0.00)) != 0){
                    BigDecimal retorno = arredondar(produto.getImposto());
                    produto.setImposto(retorno);
                    produto.setPreco(produto.getPreco().add(produto.getImposto()));
                }
            }
        }
    }

    private static BigDecimal arredondar(BigDecimal imposto) {

        BigDecimal impostoArredondado = imposto.divide(new BigDecimal(5));
        BigDecimal rounded = impostoArredondado.setScale(2, RoundingMode.HALF_UP);
        BigDecimal ajustado = rounded.multiply(new BigDecimal(5));

        return ajustado;
    }

    private static Produtos totalizacao(Produtos produto, int codProd) {

        if(codProd == 1){
            produto.setCodigo(1);
            produto.setCategoria(1);
            produto.setImportado(false);
            produto.setPreco(new BigDecimal(12.49));
            produto.setDescricao("Livro");
        }else if(codProd == 2){
            produto.setCodigo(2);
            produto.setCategoria(2);
            produto.setImportado(false);
            produto.setPreco(new BigDecimal(14.99));
            produto.setDescricao("CD de música");
        }else if(codProd == 3){
            produto.setCodigo(3);
            produto.setCategoria(3);
            produto.setImportado(false);
            produto.setPreco(new BigDecimal(0.85));
            produto.setDescricao("Barra de chocolate");
        }else if(codProd == 4){
            produto.setCodigo(4);
            produto.setCategoria(3);
            produto.setImportado(true);
            produto.setPreco(new BigDecimal(10.00));
            produto.setDescricao("Caixa de chocolate importada");
        }else if(codProd == 5){
            produto.setCodigo(5);
            produto.setCategoria(5);
            produto.setImportado(true);
            produto.setPreco(new BigDecimal(47.50));
            produto.setDescricao("Frasco de perfume importado");
        }else if(codProd == 6){
            produto.setCodigo(6);
            produto.setCategoria(5);
            produto.setImportado(true);
            produto.setPreco(new BigDecimal(27.99));
            produto.setDescricao("Frasco de perfume2 importado");
        }else if(codProd == 7){
            produto.setCodigo(7);
            produto.setCategoria(5);
            produto.setImportado(false);
            produto.setPreco(new BigDecimal(18.99));
            produto.setDescricao("Frasco de perfume");
        }else if(codProd == 8){
            produto.setCodigo(8);
            produto.setCategoria(8);
            produto.setImportado(false);
            produto.setPreco(new BigDecimal(9.75));
            produto.setDescricao("Pacote de comprimidos para dor de cabeça");
        }else if(codProd == 9){
            produto.setCodigo(9);
            produto.setCategoria(3);
            produto.setImportado(true);
            produto.setPreco(new BigDecimal(11.25));
            produto.setDescricao("Caixa de chocolate2 importada");
        }else {
            System.out.println("Código inválido");
        }
        produto.setQuantidade(1);
        return produto;
    }

}
