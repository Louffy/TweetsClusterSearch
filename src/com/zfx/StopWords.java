package com.zfx;

import java.util.ArrayList;

public class StopWords {
	private String[] stopWords = {"a","about","above","ac","according","accordingly","across","actually","adj","af","after","afterwards","again",
			"against","al","albeit","all","almost","alone","along","already","als","also","although","always","am","among","amongst","amoungst","amount",
			"an","and","another","any","anybody","anyhow","anyone","anything","anyway","anywhere","ap","apart","apparently","are","aren","arise","around",
			"as","aside","at","au","auf","aus","aux","av","avec","away","b","back","be","became","because","become","becomes","becoming","been","before",
			"beforehand","began","begin","beginning","begins","behind","bei","being","below","beside","besides","best","better","between","beyond","bill",
			"billion","both","bottom","briefly","but","by","c","call","came","can","cannot","canst","cant","caption","captions","certain","certainly","cf",
			"choose","chooses","choosing","chose","chosen","clear","clearly","co","come","comes","computer","con","contrariwise","cos","could","couldn",
			"couldnt","cry","cu","d","da","dans","das","day","de","degli","dei","del","della","delle","dem","den","der","deren","des","describe","detail",
			"di","did","didn","die","different","din","do","does","doesn","doing","don","done","dos","dost","double","down","du","dual","due","durch","during",
			"e","each","ed","eg","eight","eighty","either","el","eleven","else","elsewhere","em","empty","en","end","ended","ending","ends","enough","es",
			"especially","et","etc","even","ever","every","everybody","everyone","everything","everywhere","except","excepted","excepting","exception",
			"excepts","exclude","excluded","excludes","excluding","exclusive","f","fact","facts","far","farther","farthest","few","ff","fifteen","fifty",
			"fify","fill","finally","find","foer","follow","followed","following","follows","for","former","formerly","forth","forty",
			"forward","found","four","fra","frequently","from","front","fuer","full","further","furthermore","furthest","g","gave","general","generally",
			"get","gets","getting","give","given","gives","giving","go","going","gone","good","got","great","greater","h","had","haedly","half","halves",
			"hardly","has","hasn","hasnt","hast","hath","have","haven","having","he","hence","henceforth","her","here","hereabouts","hereafter","hereby",
			"herein","hereto","hereupon","hers","herself","het","high","higher","highest","him","himself","hindmost","his","hither","how","however",
			"howsoever","hundreds","i","if","ihre","ii","im","immediately","important","in","inasmuch","inc","include","included","includes",
			"including","indeed","indoors","inside","insomuch","instead","interest","into","inward","is","isn","it","its","itself","j","ja","journal","journals",
			"just","k","kai","keep","keeping","kept","kg","kind","kinds","km","l","la","large","largely","larger","largest","las","last","later","latter",
			"latterly","le","least","les","less","lest","let","like","likely","little","ll","long","longer","los","low","lower","lowest","ltd","m","made",
			"mainly","make","makes","making","many","may","maybe","me","meantime","meanwhile","med","might","mill","million","mine","miss","mit","more",
			"moreover","most","mostly","move","mr","mrs","ms","much","mug","must","my","myself","n","na","nach","name","namely","nas","near","nearly",
			"necessarily","necessary","need","needed","needing","needs","neither","nel","nella","never","nevertheless","new","next","nine","ninety",
			"no","nobody","none","nonetheless","noone","nope","nor","nos","not","note","noted","notes","nothing","noting","notwithstanding","now",
			"nowadays","nowhere","o","obtain","obtained","obtaining","obtains","och","of","off","often","og","ohne","ok","old","om","on","once","onceone",
			"one","only","onto","or","ot","other","others","otherwise","ou","ought","our","ours","ourselves","out","outside","over","overall","owing",
			"own","p","par","para","part","particular","particularly","past","per","perhaps","please","plenty","plus","por","possible","possibly","pour",
			"poured","pouring","pours","predominantly","previously","pro","probably","prompt","promptly","provide","provided","provides","providing","put",
			"q","quite","r","rather","re","ready","really","recent","recently","regardless","relatively","respectively","reuters","round","s","said","same",
			"sang","save","saw","say","second","see","seeing","seem","seemed","seeming","seems","seen","sees","seldom","self","selves","send","sending",
			"sends","sent","serious","ses","seven","seventy","several","shall","shalt","she","short","should","shouldn","show","showed","showing","shown",
			"shows","si","side","sideways","significant","similar","similarly","simple","simply","since","sincere","sing","single","six","sixty","sleep",
			"sleeping","sleeps","slept","slew","slightly","small","smote","so","sobre","some","somebody","somehow","someone","something","sometime","sometimes",
			"somewhat","somewhere","soon","spake","spat","speek","speeks","spit","spits","spitting","spoke","spoken","sprang","sprung","staves","still","stop",
			"strongly","substantially","successfully","such","sui","sulla","sung","supposing","sur","system","t","take","taken","takes","taking","te","ten","tes",
			"than","that","the","thee","their","theirs","them","themselves","then","thence","thenceforth","there","thereabout","thereabouts","thereafter","thereby",
			"therefor","therefore","therein","thereof","thereon","thereto","thereupon","these","they","thick","thin","thing","things","third","thirty","this","those",
			"thou","though","thousand","thousands","three","thrice","through","throughout","thru","thus","thy","thyself","til","till","time","times","tis","to","together",
			"too","top","tot","tou","toward","towards","trillion","trillions","twelve","twenty","two","u","ueber","ugh","uit","un","unable","und","under","underneath",
			"unless","unlike","unlikely","until","up","upon","upward","us","use","used","useful","usefully","user","users","uses","using","usually","v","van","various",
			"ve","very","via","vom","von","voor","vs","w","want","was","wasn","way","ways","we","week","weeks","well","went","were","weren","what","whatever","whatsoever",
			"when","whence","whenever","whensoever","where","whereabouts","whereafter","whereas","whereat","whereby","wherefore","wherefrom","wherein","whereinto","whereof",
			"whereon","wheresoever","whereto","whereunto","whereupon","wherever","wherewith","whether","whew","which","whichever","whichsoever","while","whilst","whither",
			"who","whoever","whole","whom","whomever","whomsoever","whose","whosoever","why","wide","widely","will","wilt","with","within","without","won","worse","worst",
			"would","wouldn","wow","x","xauthor","xcal","xnote","xother","xsubj","y","ye","year","yes","yet","yipee","you","your","yours","yourself","yourselves","yu",
			"bit","fb","feel","coming","days","dear","mp","oh","ow","net","right","says","thanks","think","thought","today","try","trying",
			"een","look","looks","gd","hey","z","za","ze","zu","zum","color='red'",""," ","http","ly","bit","rt","ya","-","com","www","know","goes","--","anti-","gl","goo","haha","love","nice","org",
			"years","big","lol","people","que","rise","watch","yg","win","free","retweet","red","color","font","blue"};
	public ArrayList removeStopWords(ArrayList<String> words){
		for(int i = 0;i < stopWords.length;i ++){
			if(words.indexOf(stopWords[i]) != -1){
				while(words.indexOf(stopWords[i]) != -1)
				words.remove(stopWords[i]);
			}
				
		}
		return words;
	}

}
