package application;

public class Movie {
	private int movieId;
	private String movieTitle;
	private String movieGenre;
	private String posterUrl;
	private String summaryFilePath;
	

	Movie(int movieId,String movieTitle,String movieGenre,String posterUrl,String summaryFilePath){
		this.movieId=movieId;
		this.movieTitle=movieTitle;
		this.movieGenre=movieGenre;
		this.posterUrl=posterUrl;
		this.summaryFilePath=summaryFilePath;
		
	}
	
	
	

	public int getMovieId() {
		return this.movieId;
		
	}
	
	
	public String getMovieTitle() {
		return this.movieTitle;
		
	}
	
	public String getMovieGenre() {
		return this.movieGenre;
		
	}
	
	public String getposterUrl() {
		return this.posterUrl;
		
	}
	
	public String getSummaryFilePath() {
		return this.summaryFilePath;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
