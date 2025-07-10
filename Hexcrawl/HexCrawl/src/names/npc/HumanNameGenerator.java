package names.npc;

import data.Indexible;
import names.IndexibleNameGenerator;
import util.Util;

public class HumanNameGenerator extends IndexibleNameGenerator{
	private static final String[] FIRST = {"Adrik","Alvyn","Aurora","Eldeth","Eldon","Farris","Kathra","Kellen","Nissa","Xinli","Zorra",
			"Willoughby","Ryley","Ryle","Teddie","York","Stroud","Redford","Erwin","Unwin","Thurstan","Salisbury","Launcelot","Ransom","Thurlow","Burley","Manning","Morland","Frayne","Rockwell",
			"Lyndell","Barton","Chadwick","Radcliff","Rufford","Shelley","Kendrick","Penley","Southwell","Waite","Saunderson","Rumford","Prescott","Pollock","Aldwyn","Marlow","Huntley","Locke",
			"Oxford","Everard","Paxton","Whitcombe","Raven","Kirkwood","Aldridge","Lane","Somerset","Snowden","Welby","Marley","Rawley","Wyndam","Buckley","Haven","Radnor","Rayburn","Ainsley",
			"Roxbury","Jamieson","Rowson","Ramsden","Saxon","Wyndham","Twyford","Pelton","Linley","Walby","Renshaw","Rutledge","Norvin","Fulton","Edbert","Safford","Oglesby","Trowbridge",
			"Hargreave","Bromley","Marden","Arley","Calvert","Dalbert","Wistan","Nickson","Packard","Zale","Huxley","Thorpe","Weston","Palmer","Udolf","Wheeler","Elvy","Ramsay","Earl",
			"Tannar","Gomer","Whitfield","Lawson","Waverly","Salton","Lockwood","Kenley","Ridgley","Dempster","Wakefield","Warburton","Egerton","Hampton","Tennyson","Sherborne","Stratford",
			"Milbourn","Rockley","Whitby","Burke","Standish","Ormond","Morley","Riston","Renfred","Orman","Thormund","Sherwood","Edlin","Seger","Prentice","Risley","Haslett","Shandy",
			"Chetwin","Upwood","Lester","Norville","Mervyn","Ravinger","Ronson","Stratton","Nash","Yale","Hayes","Radford","Sheldon","Yardley","Westbrook","Bradley","Barclay","Wakeman",
			"Hagley","Rutley","Kinsey","Osmar","Sealey","Hayden","Tatum","Randal","Dwennon","Randolph","Eldric","Venn","Reading","Wentworth","Osmond","Denley","Halstead","Orford","Seaborne",
			"Routledge","Linwood","Gresham","Vail","Elwin","Calder","Holmes","Stanley","Tarrant","Tripp","Hedley","Fairley","Millard","Thatcher","Byford","Fuller","Raynold","Upton","Randell",
			"Mead","Graeme","Lawford","Thornley","Rudyard","Winchester","Haddon","Hadley","Wainwright","Derward","Rowley","Wystan","Leverton","Remington","Kemp","Brett","Kingston","Ulfred",
			"Kenrick","Devereux","Witton","Sykes","Grantham","Macy","Oxton","Delwyn","Ransley","Kirkley","Dryden","Kyne","Axton","Raleigh","Wilkes","Stowe","Radborne","Leigh","Wickham",
			"Webster","Elwood","Hollis","Reilly","Nyle","Merrill","Ridley","Fenwick","Somerville","Lancelot","Hardwin","Blaxton","Harlan","Thorne","Ivy","Lynn","Birdie","Felberta","Afton",
			"Winsome","Claiborne","Erline","Wendelle","Irvette","Fayre","Lana","Whitney","Rae","Payge","Blake","Gypsy","Twyla","Zenith","Norvella","Gerry","Hope","Timber","Louvaine","Edwina",
			"Queena","Haiden","Zanna","Piper","Cleva","Missy","Athela","Kimberley","Jolene","Alison","Skylar","Marjorie","Hertha","Lari","Winifred","Dahlia","Loveday","Gleda","Odella","Pepper",
			"Langley","Gerarda","Primrose","Briar","Gytha","Dena","Gaines","Alodie","Mertice","Timothea","Aldora","Posy","Aldercy","Eathelin","Carreen","Scarlett","Damosel","Ornelle","Alfreda",
			"Chancey","Alden","Wallis","Arden","Dana","Melba","Averil","Lindon","Zephrine","Ebba","Mildred","Udele","Flyta","Misty","Tempest","Shelby","Chaney","Allura","Ela","Perri","Trilby",
			"Nara","Teal","Daralis","Edith","Edeva","Ludella","Honbria","Leoma","Harper","Florence","Bronte","Zeta","Eletta","Philberta","Della","Carling","Grayson","Elmira","Merivale","Faith",
			"Poppy","Pixie","Eadda","Everild","Sigourney","Shelton","Jancis","Chandler","Mercy","Eartha","Lindley","Fleta","Oriel","Marigold","Harley","Beverley","Bunty","Bunny","Kaelyn","Oletha",
			"Luella","Stockard","Lodema","June","Fernley","Holly","Corliss","Windy","Lyndal","Whaley","Thistle","Velma","Emmet","Lark","Agate","Generia","Kendra","Channing","Clover","Candace",
			"Perry","Farley","Quella","Patience","Maida","Velvet","Cleantha","Madison","Wanetta","Eda","Pebbles","Lauren","Levina","Dooriya","Ember","Pamela","Edmonda","Tuesday","Riley","Paige",
			"Cerelia","Opeline","Bernia","Westina","Halsey","Tyler","Kestrel","Walker","Darrene","Brooke","Bliss","Annice","Elvina","Goldie","Lena","Godiva","Yetta","Rue","Roberta","Carrington",
			"Wesley","Tawnie","Darby","Sparrow","Starr","Alvina","Brighton","Ulrika","Nellwyn","Forestyne","Dawn","Edda","Cedrica","Bonnie","Fern","Doanne","Robyn","Ellery","Wren","Earna",
			"Leanne","Whitley","Imogene","Shirley","Bedelia","Cherilyn","Skyla","Orlan","Tory","Lassie","Nelda","Iria","Louella","Carlyle","Haylee","Salal","Lorna","Heather","Beda","Hazel",
			"Tiffany","Lee","Edrea","Merry","Berthilda","Lillian","Charlotte","Laibrook","Fernleigh","Firth","Goddard","Rowell","Freeman","Stanhope","Jefferson","Stockton","Townsend","Haig",
			"Ashliegh","Cromwell","Stanfield","Hamilton","Tilford","Presley","Patton","Athelstan","Yule","Heathcote","Ryesen","Blossom","Rumer","Edolie","Rainbow","Duchess","Valora","Farrah",
			"Vulpine","Dale","Audrey","Daisy","Avery","Joyce","Rowena","Lucianna","Spring","Petula","Sunniva","Willow","Waynette","Geraldine","Braeden","Cam","Dell","Landon","Storm","Starling",
			"Edwerdina","Whoopi","Devona","Radella","Zelene","Roden","Langworth","Scott","Alger","Fane","Stanford","Ronald","Crewe","Bray","Tranter","Kenton","Sunny","Blythe","Eden","Elethea",
			"Edlyn","Wilona","Ashley","Lona","Janelle","Haralda","Linford","Litton","Shepherd","Vane","Morton","Seabrook","Sydney","Fielding","Sanborn","Reginald","Westby","Radcliffe","Melinda",
			"Maitane","Wheaton","Elsdon","Thornton","Rochester","Richelle","Wylie","Tinble","Eostre","Daffodil","Norma","Unity","Ranald","Warmund","Seabert","Osma","Warley","Rushford","Walwyn",
			"Reynold","Gladstone","Ridgeway","Livingston","Norwood","Milford","Medwin","Dallin"};
	private static final String[] LAST = {"${color}${element}","${material}fist","${color}castle","Good${item}","${color}beard","${material}beard",
			"${ethereal form}${landmark}","Brightsun","Dundragon","Garrick","Jaerin","Merryweather","Wren"};

	public static String getLastName(int... val) {
		if(val.length<3) throw new IllegalArgumentException("Expected 3 or more values");
		int[] remainder = Util.getRemainder(val, 1);
		return Util.formatTableResult(getElementFromArray(LAST,val[0]),new Indexible(remainder));
	}
	public static String getLastName(Indexible obj) {
		return Util.formatTableResult(getElementFromArray(LAST,obj),obj);
	}


	@Override
	public String getName(Indexible obj) {
		return getElementFromArray(FIRST,obj)+" "+getLastName(obj);
	}



}
