package me.dio.soocergirls.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.suellenmuniz.soocergirls.R;
import com.suellenmuniz.soocergirls.databinding.NewsItemBinding;

import java.util.List;

import me.dio.soocergirls.data.SoocerNewsRepository;
import me.dio.soocergirls.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> newsList;
    private final FavoriteListener favoriteListener;

    public NewsAdapter(List<News> newsList, FavoriteListener favoriteListener) {
        this.newsList = newsList;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(inflater, parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
      News news = this.newsList.get(position);
      holder.bind(news);

      // Implementação da Funcionalidade de "abrir link":

        holder.binding.btOpenLink.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(news.link));
            holder.binding.getRoot().getContext().startActivity(intent);

        });
        // Implementação da Funcionalidade de "Compartilhar":
        holder.binding.ivShare.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, news.title);
            intent.putExtra(Intent.EXTRA_TEXT, news.link);
            holder.binding.getRoot().getContext().startActivity(
                    Intent.createChooser(
                            intent,
                            "Share link using"
                    ));
        });
        // Implementação da Funcionalidade de "Favoritar" (o evento será instanciado pelo fragment):
        holder.binding.ivFavorite.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            favoriteListener.onFavoriteClicked(news);
            notifyItemChanged(position);
        });
        if (news.favorite) {
            holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_icon);
        } else {
            holder.binding.ivShare.setImageResource(R.drawable.ic_favorite_off);
            AsyncTask.execute(() -> {
                SoocerNewsRepository.getInstance().getLocalDb().newsDao().delete(news);
            });
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public NewsViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(News news) {
            binding.tvTitle.setText(news.title);
            binding.tvDescription.setText(news.description);
            Picasso.get().load(news.image)
                    .fit()
                    .into(binding.ivThumbnail);

        }
    }
    public interface FavoriteListener {
        void onFavoriteClicked(News news);
    }

   }

