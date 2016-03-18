/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.17 : Database - clojure
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`clojure` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `clojure`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(200) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `recipe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  KEY `recipe` (`recipe`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`recipe`) REFERENCES `recipe` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=903 DEFAULT CHARSET=latin1;

/*Data for the table `comment` */

insert  into `comment`(`id`,`text`,`user`,`recipe`) values (901,'nice',3215,1540),(902,'nice',3216,1543);

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `value` int(1) NOT NULL,
  `user` int(11) NOT NULL,
  `recipe` int(11) NOT NULL,
  PRIMARY KEY (`value`,`user`,`recipe`),
  KEY `user` (`user`),
  KEY `rating_ibfk_2` (`recipe`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`recipe`) REFERENCES `recipe` (`id`) ON DELETE CASCADE,
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`value`,`user`,`recipe`) values (0,3215,1545),(1,3215,1540),(1,3215,1541),(1,3215,1543),(1,3215,1544),(0,3216,1541),(0,3216,1544),(0,3216,1545),(1,3216,1540),(1,3216,1543),(1,3217,1540),(1,3217,1541),(1,3217,1543),(1,3217,1544),(1,3217,1545);

/*Table structure for table `recipe` */

DROP TABLE IF EXISTS `recipe`;

CREATE TABLE `recipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `body` text,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  CONSTRAINT `recipe_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1546 DEFAULT CHARSET=latin1;

/*Data for the table `recipe` */

insert  into `recipe`(`id`,`title`,`body`,`user`) values (1540,'Veggie meatballs with tomato courgetti','Ingredients\r\n\r\n3 garlic cloves\r\nFor the veggie meatballs\r\n2 tsp rapeseed oil\r\n, plus extra for greasing\r\n1 small onion\r\n, very finely chopped\r\n2 tsp balsamic vinegar\r\n100g canned red kidney beans\r\n1 tbsp beaten egg\r\n1 tsp tomato purée\r\n1 heaped tsp chilli powder\r\n½ tsp ground coriander\r\n15g ground almonds\r\n40g cooked sweetcorn\r\n2 tsp chopped thyme leaves\r\nFor the tomato courgetti\r\n2 large or 3 normal tomatoes\r\n, chopped\r\n1 tsp tomato purée\r\n1 tsp balsamic vinegar\r\n2 courgettes\r\n cut into \'noodles\' with a spiralizer, julienne peeler, or by hand\r\n\r\nMethod\r\n\r\nFinely chop the garlic. Heat the oil in a large pan and fry the onion, stirring frequently, for 8 mins. Stir in the balsamic vinegar and cook for 2 mins more. Meanwhile, put the beans in a bowl with the egg, tomato purée and spices, and mash until smooth. Stir in the almonds and sweetcorn with the thyme, a third of the chopped garlic and the balsamic onions. Mix well and shape into about 8 balls the size of a walnut, and place on a baking tray lined with oiled baking parchment.\r\nHeat oven to 220C/200C fan/gas 7 and bake the veggie meatballs for 15 mins until firm. Meanwhile, put the tomatoes, tomato purée and balsamic vinegar in a pan and cook with 2-3 tbsp water until pulpy, then stir in the remaining garlic and courgetti. Turn off the heat as you want to warm the noodles rather than cook them. Serve with the veggie meatballs.',3215),(1541,'Vegan cashew Parmesan','Ingredients\r\n\r\n150g cashews\r\n¼ tsp garlic powder\r\n4 tbsp nutritional yeast\r\n\r\nMethod\r\n\r\nPlace all the ingredients and 1 tsp salt in a small food processor and blitz until it becomes a coarse powder. Transfer to an airtight container and store in the cupboard for up to 1 week.',3215),(1543,'Bone builder smoothie','Ingredients\r\n\r\n½ avocado\r\n, peeled, stoned and roughly chopped\r\ngenerous handful spinach\r\ngenerous handful kale\r\n, washed well\r\n50g pineapple\r\n chunks\r\n10cm piece cucumber, roughly chopped\r\n300ml coconut\r\n water\r\n\r\nMethod\r\n\r\nPut the avocado, spinach, kale, pineapple and cucumber in the blender, top up with coconut water, then blitz until smooth.',3216),(1544,'Bean salad with yogurt avocado dressing','Ingredients\r\n\r\n2 round wholemeal pitta bread, split in half and cut into triangles\r\n200g frozen broad bean\r\n1 avocado\r\n, flesh scooped out\r\nsmall pack parsley\r\n8 tbsp low-fat natural yogurt\r\n1 garlic clove, roughly chopped\r\n1 lemon\r\n, zest of ½, juice of whole\r\n2 Little Gem lettuce, roughly chopped\r\n400g can white bean, rinsed and drained (we used cannellini beans)\r\n4 spring onion\r\n, finely chopped\r\n2 carrot\r\n, peeled and grated\r\n10 radish\r\n, halved\r\nhandful of cress, snipped\r\n\r\nMethod\r\n\r\nHeat grill to high. Spread the pitta triangles out in a shallow baking tray. Toast for a couple of mins to crisp, turning once. Keep a close eye on them otherwise they will burn. Once toasted, remove and place to one side. Next, pop the broad beans in boiling water and cook for 2-3 mins, then drain and remove the bright green pod from the hard outer shell.\r\nIn a blender, whizz together the avocado, parsley, yogurt, garlic, lemon juice and zest and seasoning.\r\nPut the remaining ingredients in a bowl, except the cress. Toss together with the avocado and yogurt dressing, then sprinkle over the pitta croutons and cress. Eat straight away.',3217),(1545,'Moroccan roasted vegetable soup','Ingredients\r\n\r\n1 red onion, cut into 8 wedges\r\n300g carrot\r\n, cut into 2cm chunks\r\n300g parsnip\r\n, cut into 2cm chunks\r\n300g peeled butternut squash, cut into 2cm chunks\r\n1 small potato\r\n, cut into 2cm chunks\r\n2 garlic clove\r\n1 tbsp ras el hanout\r\n1½ tbsp olive oil\r\n1.3l hot vegetable stock\r\n6 tbsp Greek-style yogurt and 1 tbsp finely chopped mint, to serve (optional)\r\n\r\nMethod\r\n\r\nHeat oven to 200C/180C fan/gas 6. Tip all the vegetables and the garlic into a roasting tin. Sprinkle over the ras el hanout and some seasoning, drizzle over the oil and give everything a good stir. Roast for 30-35 mins, turning the vegetables over halfway, until they’re tender and starting to caramelise a little.\r\nTransfer the roasted veg to a large saucepan, pour over the hot stock and simmer for 5 mins. Pure?e the soup in a food processor, or in the pan with a hand blender, until smooth, then ladle into a flask for work. If eating at home, serve with a dollop of yogurt, a scattering of mint and a grinding of black pepper.',3217);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3218 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`) values (3215,'Dragana','dragana'),(3216,'Ena','ena'),(3217,'Marko','marko');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
