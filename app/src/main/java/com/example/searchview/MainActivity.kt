package com.example.searchview


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var mAdapter : PostContentAdapter? = null
    private lateinit var postList : ArrayList<Post>
    private lateinit var searchPost : SearchView
    private lateinit var rePost : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rePost = findViewById(R.id.recyclerView)
        searchPost = findViewById(R.id.searchView)
        searchPost.setOnQueryTextListener(searchViewTextListner)

        postList = temp()
        initAdapter()
    }

    var searchViewTextListner : SearchView.OnQueryTextListener =
        object :SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 근데 검색버튼이 없으므로 사용x
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            //텍스트 입력/수정 시 호출
            override fun onQueryTextChange(s: String?): Boolean {
                mAdapter!!.filter.filter(s)
                return false
            }

        }


    fun initAdapter() {
        rePost.layoutManager = LinearLayoutManager(this)
        mAdapter = PostContentAdapter(postList, this)
        rePost.adapter = mAdapter
    }

    fun temp() : ArrayList<Post> {
        var t = ArrayList<Post>()
        t.add(Post(1, "wpqkf@gmail.com"))
        t.add(Post(2, "ehofk@gmail.com"))
        t.add(Post(3, "gngk@gmail.com"))
        t.add(Post(4, "gmgl@gmail.com"))

        return t
    }



    /*inner class PostContentAdapter(var post : ArrayList<Post>) : RecyclerView.Adapter<PostContentAdapter.postViewHolder>(), Filterable {
        var filterPost = ArrayList<Post>()
        var postFilter = PostFilter()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewHolder {
            val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.post_item,parent,false)

            return postViewHolder(view)
        }

        override fun onBindViewHolder(holder: postViewHolder, position: Int) {
            holder.setData(filterPost[position],position)
        }

        override fun getItemCount(): Int {
            return filterPost.size
        }

        override fun getFilter(): Filter {
            return postFilter
        }

        inner class postViewHolder(postView : View) : RecyclerView.ViewHolder(mBinding.root) {
            private var position : Int? = null

            fun setData(post: Post, position: Int) {
                this.position = position
                postItemBinding.accountEmail.text = post.email

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
                    results.values = postList
                    results.count = postList.size
                    return results

                    //20글자 수 이하일 때 -> 이메일로 검색
                } else if (filterString.trim {it <= ' '}.length <= 20) {
                    for (searchEmail in postList) {
                        if (searchEmail.email.contains(filterString)) {
                            filterList.add(searchEmail)
                        }
                    }
                }
                results.values = filterList
                results.count = filterList.size

                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterPost.clear()
                filterPost.addAll(results!!.values as ArrayList<Post>)
                notifyDataSetChanged()
            }
        }
    }*/
}