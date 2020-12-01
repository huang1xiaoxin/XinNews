package com.hx.app.xinnews.bean

class ResultContent<T> {
    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"status":"0","msg":"ok","result":["头条","新闻","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"]}
     */
     var code: String? = null
     var charge = false
     var msg: String? = null

    /**
     * status : 0
     * msg : ok
     * result : ["头条","新闻","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"]
     */
     var result: T? = null


}