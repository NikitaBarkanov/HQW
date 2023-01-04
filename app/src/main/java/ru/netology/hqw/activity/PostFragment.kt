package ru.netology.hqw.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.hqw.R
import ru.netology.hqw.activity.NewPostFragment.Companion.textArg
import ru.netology.hqw.adapter.PostViewHolder
import ru.netology.hqw.databinding.PostFragmentBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.listeners.OnInteractionListeners
import ru.netology.hqw.viewModel.PostViewModel

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = PostFragmentBinding.inflate(layoutInflater)

        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

        val viewHolder = PostViewHolder(binding.postLayout, object : OnInteractionListeners {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onReply(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val replyIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(replyIntent)
            }

            override fun onYoutube(post: Post) {
                if (post.video != null) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(post.video)
                    }
                    startActivity(intent)
                }
            }

            override fun postLink(post: Post) {
                findNavController().navigateUp()
            }
        })
        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            findNavController()
                .navigate(R.id.action_feedFragment_to_newPostFragment, Bundle().apply {
                    textArg = post.content
                })

        }
        val postId = arguments?.textArg
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id.toString() == postId } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }

        return binding.root
    }
}