package fr.m2i.journal2014;

import java.sql.Connection;
import java.sql.PreparedStatement;

import fr.m2i.journal2014.models.DbConnexion;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ArticleSuppression extends Activity implements OnClickListener {
	
	
	private Spinner  spinnerSeletionnerArticle;
	private EditText editTextNumero;
	private EditText editTextDate;
	private EditText editTextArticleTitre;
	private EditText editTextArticleChapeau;
	private EditText editTextArticleResume;
	private EditText editTextArticleTexte;
	private TextView textViewArticleContributeur;
	private TextView textViewArticleCategorie;
	private TextView textViewArticleRubrique;
	private TextView textViewArticleMotCle;
	private Button   buttonSuppression;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_suppression);
		
		
		initInterface();

   }
	
	private void initInterface() {

		spinnerSeletionnerArticle = (Spinner) findViewById(R.id.spinnerSeletionnerArticle);
		editTextNumero = (EditText) findViewById(R.id.editTextNumero);
		editTextDate = (EditText) findViewById(R.id.editTextDate);
		editTextArticleTitre = (EditText) findViewById(R.id.editTextArticleTitre);
		editTextArticleChapeau = (EditText) findViewById(R.id.editTextArticleChapeau);
		editTextArticleResume = (EditText) findViewById(R.id.editTextArticleResume);
		editTextArticleTexte = (EditText) findViewById(R.id.editTextArticleTexte);
		textViewArticleContributeur = (TextView) findViewById(R.id.textViewArticleContributeur);
		textViewArticleCategorie = (TextView) findViewById(R.id.textViewArticleCategorie);
		textViewArticleRubrique = (TextView) findViewById(R.id.textViewArticleRubrique);
		textViewArticleMotCle = (TextView) findViewById(R.id.textViewArticleMotCle);
		buttonSuppression = (Button) findViewById(R.id.buttonSuppression);

		
		buttonSuppression.setOnClickListener(this);

	}
	
	
	private static void supprimmerArticle() throws SQLException {
		 
		Connection cnx = null;
		PreparedStatement preparedStatement = null;
 // ma requete préparer poru un delete
		String deleteSQL = "DELETE  FROM journal2014.article article WHERE  article.id_article = idArticle;";
 
		try {
			cnx = DbConnexion.connect();
			preparedStatement = cnx.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, 1001);
 
			//Exécuter supprimer SQL stetement 
			preparedStatement.executeUpdate();
 
			System.out.println("L'article est supprimmé!");
 
		} catch (Exception e) {
 
			System.out.println(e.getMessage());
 
		}
	}
		
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v == buttonSuppression) {
			
		}
		
	}
}