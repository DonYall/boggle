
public class Projectvar {

	public static void main(String[] args) {
		String x ="cringe";
		int p1Score = score(x);
		System.out.println(p1Score);
	}
	public static int score(String x) {
		int p1Score = 0;
		if (x.length()==3||x.length()==4 ) {
			p1Score +=1;
		}
		else if (x.length()==5 ) {
			p1Score +=2;
		}
		else if (x.length()==6 ) {
			p1Score +=3;
		}
		else if (x.length()==7 ) {
			p1Score +=4;
		}
		else if (x.length()>=8 ) {
			p1Score +=11;
		}
		return p1Score;
	}
}


