CREATE TABLE `feeditems` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(250) default NULL,
  `link` varchar(250) default NULL,
  `content` longtext ,
  `pubdate` datetime,
   PRIMARY KEY  (`id`)
) 
