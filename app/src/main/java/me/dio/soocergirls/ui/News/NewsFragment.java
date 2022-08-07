package me.dio.soocergirls.ui.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.suellenmuniz.soocergirls.R;
import com.suellenmuniz.soocergirls.databinding.FragmentNewsBinding;

import me.dio.soocergirls.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));

        observeNews();
        observeStates();
        binding.srlNews.setOnRefreshListener(newsViewModel::findNews);

        return root;
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news ->
            binding.rvNews.setAdapter(new NewsAdapter(news, newsViewModel::saveNews)));

    }

    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            binding.srlNews.setColorSchemeColors(getResources().getColor(R.color.pink));
            switch (state) {

                case DOING:
                    binding.srlNews.setRefreshing(true);

                    break;
                case DONE:
                    binding.srlNews.setRefreshing(false);

                    break;
                case ERROR:
                    binding.srlNews.setRefreshing(false);
                    Toast.makeText(getContext(), "Error network", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    }



