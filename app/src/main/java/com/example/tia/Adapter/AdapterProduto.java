package com.example.tia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tia.Model.Produto;
import com.example.tia.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.ProdutoViewHolder> {

    private Context context;
    private List <Produto> produtoList;

    public AdapterProduto(Context context, List<Produto> produtoList) {
        this.context = context;
        this.produtoList = produtoList;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        itemLista = layoutInflater.inflate(R.layout.produto_item,parent,false);
        return new ProdutoViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Glide.with(context).load(produtoList.get(position).getFoto()).into(holder.foto);
        holder.nome.setText(produtoList.get(position).getNome());
        holder.preco.setText(produtoList.get(position).getPreco());
        //holder.descricao.setText(produtoList.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView foto;
        private TextView nome;
        private TextView preco;
        private TextView descricao;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.fotoProduto);
            nome = itemView.findViewById(R.id.nomeProduto);
            preco = itemView.findViewById(R.id.precoProduto);
            descricao = itemView.findViewById(R.id.dtDescricaoProduto);
        }
    }
}
