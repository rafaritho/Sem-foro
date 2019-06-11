# Sem-foro
Projeto de SO 

Como compilar o programa:

Com o JDK (Java SE Development Kit) instalado, pode ser usado o javac, que é um compilador para a linguagem Java.

Para compilar o código java, o comando é o seguinte:

$ javac Main.java 

Após executado o comando acima, se não for informado NADA no seu terminal é porque a compilação foi executada com sucesso. Posteriormente, pode-se notar que foi gerado um arquivo no diretório em que seu código foi compilado.

Para executá-lo, execute o comando abaixo:

$ java Main

Esse arquivo gerado nada mais é que seu programa java compilado.

Passo a passo: https://i.imgur.com/NjXvgGE.png

Como provar que ele funciona: 

O programa usa um semáforo para controlar o acesso à variável count, que é uma variável estática dentro da classe Shared. Shared.count é incrementado cinco vezes pelo thread "A" e diminuído cinco vezes pelo thread "B".
Para evitar que esses dois threads acessem o Shared.count ao mesmo tempo, o acesso é permitido somente depois que uma permissão é adquirida do semáforo controlador. Após o acesso estar completo, a permissão é liberada. Dessa forma, apenas um thread por vez acessará o Shared.count, como mostra a saída.
Sem o uso do semáforo, os acessos ao Shared.count pelos dois threads teriam ocorrido simultaneamente, e os incrementos e decrementos seriam misturados. Exemplo: https://i.imgur.com/ohzvCpD.png
