package screens.product

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.select.Elements
import screens.BaseScreenModel

class ProductScreenModel : BaseScreenModel<Product>() {
    override suspend fun fetchData() {
        val tmpProduct = mutableListOf<Product>()
        val url = "https://coffeelab.com.tr/product-category/sicak-icecekler/page/2/?orderby=menu_order"

        val doc = Ksoup.parseGetRequest(url)
        val content = doc.select("div.lte-item")
        println("__content___$content")
        content.forEach {
            val name = it.select("h2.woocommerce-loop-product__title").text()
            val imgUrl = it.extractImageUrlsBySize()["default"]
            val link = it.select("a").attr("href")
            tmpProduct.add(
                Product(name, imgUrl, link)
            )
        }

        updateData(tmpProduct)
    }
}

/**
 * Extract ımage urls by size
 *
 * @return [default,480w,200w,683w,768w,1024w]
 */
fun Element.extractImageUrlsBySize(): Map<String, String> {
    val imageMap = mutableMapOf<String, String>()

    val imageElements = this.select("div.lte-image img")

    for (element in imageElements) {
        // src doğrudan "fallback" olarak eklenir
        element.attr("src").takeIf { it.isNotBlank() }?.let { src ->
            imageMap["default"] = src
        }

        // srcset parçalanır ve boyut - url eşleşmeleri çıkarılır
        element.attr("srcset").takeIf { it.isNotBlank() }?.let { srcSet ->
            srcSet.split(",").forEach { entry ->
                val parts = entry.trim().split(" ")
                if (parts.size >= 2) {
                    val url = parts[0]
                    val size = parts[1] // örn: "480w"
                    imageMap[size] = url
                }
            }
        }
    }

    return imageMap
}
