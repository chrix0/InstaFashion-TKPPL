package com.PisangHitam.InstaFashion

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classItemBasket
import com.PisangHitam.InstaFashion.classOASession
import com.PisangHitam.InstaFashion.classProduk
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.NumberFormat

object singletonData {

    //1. DATABASE + METHOD FITUR =============================================

    // LOGIN SIGNUP + BASKET
    var accList : MutableList<classAccount> = mutableListOf(
        classAccount(
            0,
            "TEST",
            "TEST",
            "Test Account",
            "testaccount@gmail.com",
            mutableListOf(),
            mutableListOf("","","",""),
            "",
            mutableListOf()
        )
    )

    var currentAccId : Int = 0

    fun subtotalInCart() : Int{
        var total = 0
        for (i : classItemBasket in accList[currentAccId].cartContent){
            total += i.hargaProduk * i.quantity
        }
        return total
    }

    fun totalInCart() : Int = subtotalInCart() + 5000

    // SHOP PRODUCTS
    var petFoodList : MutableList<classProduk> = mutableListOf(
        classProduk(
            1,
            "Royal Canin Veterinary Dog - Hypoallergenic DR 21 2kg",
            390000,
            "https://shop-cdn-m.mediazs.com/bilder/royal/canin/veterinary/dog/hypoallergenic/dr/4/400/81168_elvetis_royalcanin_veterinaydiet_hypoallergenic_4.jpg",
            "Complete dry food for dogs over one year, with food intolerances. Especially formulated to reduce ingredient and nutrient intolerances. With selected sources of protein and carbohydrate",
        ),
        classProduk(
            2,
            "Royal Canin Maxi Adult 4kg",
            340000,
            "https://shop-cdn-m.mediazs.com/bilder/royal/canin/maxi/adult/8/400/80729_pla_royalcanin_maxiadult_15kg_hs_01_8.jpg",
            "Balanced dry food for large dogs (26-45kg) between the ages of 15 months and 5 years, designed to support a healthy immune system and smooth joint function, with extra large kibble and weight control.",
        ),
        classProduk(
            3,
            "Animonda vom Feinsten Adult Grain-Free (Poultry & Veal) 6 x 150g",
            98000,
            "https://shop-cdn-m.mediazs.com/bilder/animonda/vom/feinsten/adult/grainfree/x/g/8/400/80676_pla_animonda_vomfeinsten_adult_mitgefluegel_kalb_150g_hs_01_8.jpg",
            "Complete adult dog food with plenty of meat. No added grain or soya, very tasty with high quality meat. Even for food-sensitive dogs & no artificial additives.",
        ),
        //Add stuff
        classProduk(
            4,
            "Almo Nature 6 x 70g",
            110000,
            "https://shop-cdn-m.mediazs.com/bilder/almo/nature/x/g/0/400/64491_pla_almonature_huhn_mit_kaese_0.jpg",
            "Natural wet cat food with a minimum 60% meat and fish content. It is gluten-free and full of nutrients, containing no artificial additives & available in a range of delicious, mouth-watering flavours."
        ),
        classProduk(
            5,
            "Felix As Good As It Looks Mega Pack (Beef, Chicken, Cod & Tuna) 120 x 100g",
            650500,
            "https://shop-cdn-m.mediazs.com/bilder/felix/as/good/as/it/looks/mega/pack/x/g/3/400/81762_felix_megapack_120x100g_3.jpg",
            "Now your cat can enjoy Felix As Good As It Looks complete wet cat food in a great value mega pack, with 4 different flavour varieties of juicy chunks in a flavoursome jelly and practical pouches.",
        ),
        classProduk(
            6,
            "Sanabelle Sterilised 2kg",
            260000,
            "https://shop-cdn-m.mediazs.com/bilder/sanabelle/sterilised/7/400/81412_pla_bosch_sanabelle_sterilized_2kg_7.jpg",
            "Dry food with a reduced energy content for sterilised/neutered cats 12 months+. This grain-free kibble is fibre-rich and made with lots of poultry. It will help your pet to maintain a healthy weight.",
        ),
        classProduk(
            7,
            "Applaws Pouches Cat Food in Broth Mixed Pack 12 x 70g - Chicken selection",
            237000,
            "https://shop-cdn-m.mediazs.com/bilder/applaws/pouches/cat/food/in/broth/mixed/pack/x/g/7/400/174210_7.jpg",
            "Natural, complementary cat food in a delicious broth. This tasty wet food is rich in animal protein. It is full of flavour with no artificial preservatives and colours.",
        ),
        classProduk(
            8,
            "Dokas Dried Meat Snacks - Saver Pack: 3 x 70g Dried Rabbit Meat",
            89000,
            "https://shop-cdn-m.mediazs.com/bilder/dokas/dried/meat/snacks/2/400/200596_pla_dokas_fleischsnack_huehnerbrust_streifen_250g_hs_01_2.jpg",
            "Delicious meaty snacks by Dokas, low in fat and tasty, with 93% pure meat, no added sugar, variety for your pet, strip snacks, also suitable for cats and ferrets.",
        )
    )

    var petUtensilList : MutableList<classProduk> = mutableListOf(
        classProduk(
            9,
            "Artero HIT Cordless Clipper",
            4950000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/6209/7370/09-AR01-01__36350.1590068426.gif?c=2",
            "The Artero Hit Professional Cordless Grooming Clipper give you a high-quality groom with every use. With a 5-hour battery life, this clipper is built for all day use! The Cordless Grooming Clipper also comes with two Li-Ion batteries to ensure usage for all day. With a speed of 3,000 RPM, the Artero Hit Professional Cordless Grooming Clipper works tirelessly to provide the best cut possible.",
        ),
        classProduk(
            10,
            "Artero Blades",
            300000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/7240/8387/09-AR04__74640.1594143638.gif?c=2",
            "Artero has created a blade of every size and that is fit for every occasion! The Artero Blades are meant for use with snap-on style clippers and are high quality and built to last the test of time.",
        ),
        classProduk(
            11,
            "Artero Oil Fresh Blade Cooling Spray 6.2 ounces",
            180000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/4653/5889/09-AC006-1__69372.1585244588.jpg?c=2",
            "Oil fresh blade cooling spray, instantly cools blades.  This results in a longer lasting blade life and cut.  Helps avoid rusting while also disinfecting the blade.",
        ),
        //Add stuff
        classProduk(
            12,
            "#1 All Systems Ultimate Professional Slickers",
            128000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/3998/5628/apibsfac1__88031.1563914056.1280.1280__73184.1576461636.jpg?c=2",
            "Professional quality Dog Slicker Brush comes in three sizes: small, medium, large and a triangle shape. Longer pins get all the way through the most dense coats to safely clean out dead undercoat, while not damaging top coat; soft and pliable enough for the most fragile coats.",
        ),
        classProduk(
            13,
            "#1 All Systems Ultimate De-Matting Comb",
            98000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/3789/8864/14-019__89207.1611255561.jpg?c=2",
            "The Fabulous De-Matting Comb helps you divide and conquer even the most stubborn mats. De-Matting Comb has pins which are evenly spaced. It is constructed from the most durable rust resistant steel available, the handle is made from brass & nickel then the entire comb is chrome plated. The pins are 2' long, ends and pins are very round, and smooth. The handle is 1/2' wide; comb 6' long. Pins are 5 per inch.",
        ),
        classProduk(
            14,
            "Fluff & Tuff Tiger Bone Plush Dog Toys",
            150000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/8288/9829/26-FT27__96185.1639589493.jpg?c=2",
            "The Fluff & Tuff Tiger Bone plush dog toy is built to challenge pups who love to fetch, shake, and tug. Available in two sizes, this classic bone-shaped toy is perfect for both medium and small dogs who like to snuggle at naptime or after play.",
        ),
        classProduk(
            15,
            "Alvalley Nylon Slip Collars - 6mm",
            150000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/960w/products/8293/9835/23-A06__03017.1640054466.jpg?c=2",
            "Alvalley 6mm Nylon Slip Collars are premium collars handcrafted with the finest nylon and leather. Durable and strong, this collar keeps its smooth and luxurious feel over time. The ends of these beautiful collars are stitched, then covered with hand braided leather and finished with a solid brass ring.",
        ),
        classProduk(
            16,
            "Chris Christensen White on White Shampoo - Gallon",
            835000,
            "https://cdn11.bigcommerce.com/s-3olladlsy7/images/stencil/1280x1280/products/3771/5564/03-166__02358.1575494043.jpg?c=2",
            "Unlike any other whitening shampoos, White on White is an optic intensifying treatment within a shampoo, removing yellow stains, whitening whites, and brightening all other coat colors. White on White contains no bleaching agents or caustic chemicals. Extraordinary cleansing ability and color deposit system in one application.",
        )
    )


    var petOutfitList : MutableList<classProduk> = mutableListOf(
        classProduk(
            17,
            "Hotel Doggy Utilty Style Dog Puffer Coat, Orange Rust - X-Small",
            430000,
            "https://img.chewy.com/is/catalog/289551_MAIN._AC_SY300_V1635447081_.jpg",
            "Remember to measure your pet for the paw-fect fit. Your dog will be the coolest canine on the block wearing this Hotel Doggy Utility Style Dog Puffer! This corduroy jacket and fleece hoody is made from polyester and has a warm lining to keep your buddy nice and cozy during those colder months. The Hotel Doggy Utility Dog Coat is the perfect statement piece for daily walks.",
        ),
        classProduk(
            18,
            "Frisco Dog & Cat Cable Knitted Sweater - Medium",
            180000,
            "https://img.chewy.com/is/catalog/144255_MAIN._AC_SY300_V1575302596_.jpg",
            "Remember to measure your pet for the paw-fect fit. Now your pal can stay comfy while looking dapper with the Frisco Dog & Cat Cable Knitted Sweater. This high-quality knit sweater is ultra-soft and warm so it’s perfect for chillier nights, and is easy to put on thanks to the pullover design. Dress up your pooch or kitty in this classic fashion staple whenever you’re going out or enjoying a cozy night in. Go ahead—cuddle up! This sweater feels luxuriously soft to the touch and looks just as stylish.",
        ),
        classProduk(
            19,
            "Frisco Dog & Cat Cozy Plush Fleece PJs, Llamas - Medium",
            190000,
            "https://img.chewy.com/is/catalog/268486_MAIN._AC_SY300_V1637728623_.jpg",
            "Maximize your pet’s coziness with these PJs you and your pet will both adore. They’re made of soft, thick polyester, and they fit snugly for your pet’s comfort. Plus, these jammies help limit excess shedding around the house by trapping your pet’s hair and dander, and they’ve got an adorable llama design to keep them looking cute while they cozy up and smile for their closeups during all your themed photo ops.",
        ),
        //Add stuff
        classProduk(
            20,
            "Christmas Scarf",
            45000,
            "https://cdn.shopify.com/s/files/1/0509/9919/7892/products/ChristmasScarf.jpg?v=1637673538",
            "Make sure your beloved is warm this season with the Christmas Scarf. Worn like a collar, around neck. Collar-like, knit band slides over dog's head, then wrap attached knit scarf around and tie for ultimate winter look. Great for Gifts and Valentine's and Christmas!",
        ),
        classProduk(
            21,
            "Snowflake Neckerchief - Large",
            160000,
            "https://cdn.shopify.com/s/files/1/0509/9919/7892/products/SnowflakeNeckerchief.jpg?v=1637735272",
            "Our classic christmas themed tie-on bandanas are the perfect accessory for your dapper dog",
        ),
        classProduk(
            22,
            "Sea Green Brocade Sherwani - Small",
            380000,
            "https://cdn.shopify.com/s/files/1/0509/9919/7892/products/SeaGreenBrocadeSherwani1.jpg?v=1628937533",
            "Looking for gorgeous Indian wear for pets for special occasions? Our stylish yet comfortable Sherwani for dogs is 100% custom-made as per your pet’s needs and your preferences. If you’re looking for dog dresses and occasion wear for dogs, a sherwani like this will be a perfect choice."
        ),
        classProduk(
            23,
            "Peach Brocade Sherwani - X-Large",
            380000,
            "https://cdn.shopify.com/s/files/1/0509/9919/7892/products/PeachBrocadeSherwani.jpg?v=1628937612",
            "Looking for gorgeous Indian wear for pets for special occasions? Our stylish yet comfortable Sherwani for dogs is 100% custom-made as per your pet’s needs and your preferences. If you’re looking for dog dresses and occasion wear for dogs, a sherwani like this will be a perfect choice."
        ),
        classProduk(
            24,
            "Blue Polka Dot Cape - Medium",
            100000,
            "https://cdn.shopify.com/s/files/1/0509/9919/7892/products/BluePolkadotCape.jpg?v=1625651398",
            "Stylish polka dot cape for your cute pet. Make her feel special with the cute polka dot cape. The cape is made with comfortable fabric.",
        )
    )

    //Outfit Analyzer (Untuk memudahkan resetting aja)
    lateinit var OASession : classOASession
    var allImageProcessed = false

    //2. UNIVERSAL METHOD + LIBRARY INIT ============================================

    fun formatHarga(harga : Int) : String{
        var formatter : DecimalFormat = NumberFormat.getInstance() as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(harga)
    }

    fun formatAlamat(alamat : MutableList<String>) = "${alamat[0]}, ${alamat[1]}, ${alamat[2]}, ID ${alamat[3]}"

    //Ini dipakai ketika mau ubah objek jadi JSON biar pengiriman data ke activity lain lebih gampang.
    val toJson : Gson = Gson()

    //Image Cropper. Utk OA
    fun cropThis(pic : Bitmap) : Bitmap{
        var w = pic.width
        var h = pic.height

        var wResult = w/2
        var hResult = h/2

        var dimension = Math.min(w/2, h/2)

        return ThumbnailUtils.extractThumbnail(pic, dimension, dimension)
    }
}