// Função para atualizar a quantidade de produtos no carrinho
function atualizarQuantidadeCarrinho() {
    let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    let quantidade = carrinho.reduce((total, produto) => total + produto.quantidade, 0);
    document.getElementById('quantidadeCarrinho').textContent = quantidade;

}

// Chamada da função para atualizar a quantidade de produtos no carrinho
atualizarQuantidadeCarrinho();

