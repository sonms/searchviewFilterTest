package com.example.searchview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PostContentAdapter (var post : ArrayList<Post>, var con : Context)
    : RecyclerView.Adapter<PostContentAdapter.postViewHolder>(), Filterable  {

        var filterPost = ArrayList<Post>()
        var postFilter = PostFilter()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewHolder {
            con = parent.context
            val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.post_item,parent,false)

            return postViewHolder(view)
        }

        override fun onBindViewHolder(holder: postViewHolder, position: Int) {
            val post : Post = filterPost[position]
            holder.setData(filterPost[position],position)
            holder.email.text = post.email
        }

        override fun getItemCount(): Int {
            return filterPost.size
        }

        override fun getFilter(): Filter {
            return postFilter
        }

        inner class postViewHolder(postView : View) : RecyclerView.ViewHolder(postView) {
            private var position : Int? = null
            var email : TextView

            init {
                email = postView.findViewById(R.id.account_email)
                filterPost.addAll(post)
            }

            fun setData(post: Post, position: Int) {
                this.position = position

                //postItemBinding.accountEmail.text = post.email
            }
        }



        inner class PostFilter : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterString = constraint.toString()
                val results = FilterResults()

                //검색이 필요없을 경우를 위해 원본배열 복제
                val filterList : ArrayList<Post> = ArrayList<Post>()

                //공벡제외 아무런 값도 입력하지 않았을 경우 ->원본배열
                if (filterString.trim { it <= ' '}.isEmpty()) {
                    //필터링 작업으로 계산된 모든 값
                    results.values = post
                    //필터링 작업으로 계산된 값의 수
                    results.count = post.size
                    return results

                    //20글자 수 이하일 때 -> 이메일로 검색
                } else if (filterString.trim {it <= ' '}.length <= 20) {
                    for (searchEmail in post) {
                        if (searchEmail.email.contains(filterString)) {
                            filterList.add(searchEmail)
                        }
                    }
                }
                //filterlist를 results.values에 저장
                results.values = filterList
                results.count = filterList.size

                return results
            }
            
            //결과 필터 데이터 저장
            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                filterPost.clear()
                filterPost.addAll(results.values as ArrayList<Post>)
                notifyDataSetChanged()
            }
        }
    }
