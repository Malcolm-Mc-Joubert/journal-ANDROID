package fr.m2i.journal2014;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


import fr.m2i.journal2014.models.DbConnexion;

import android.app.Activity;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author formation
 * 
 */
public class ArticleSuppression extends Activity implements OnClickListener, OnItemSelectedListener {

	// Déclaration des elements de l'interface
	private Spinner spinnerSeletionnerArticle;
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
	private Button buttonSuppression;
	private TextView textViewMessage;
	
	private ResultSet lrs;
	private Statement lstSQL;
	private Map<String, Integer> listArticle;
	private ArrayAdapter<String> aaServices;

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
		textViewMessage = (TextView) findViewById(R.id.textViewMessage);

		// Initialisation du login pour test
		editTextNumero.setText("2");
		editTextDate.setText("10/06/2014");
		editTextArticleTitre.setText("affaire d'etat");
		editTextArticleChapeau.setText("assasinat");
		editTextArticleResume.setText("Le besoin de clarification n'est tristement pas de trop, étant donné les violences ");
		editTextArticleTexte.setText("Le Monde a ainsi publié un article évoquant Samir, qui travaille dans les cuisines d'un lycée de Saint-Germain-en-Laye, dans les Yvelines, et a appris à côté de ses collègues la nouvelle de l'attentat. A ce moment-là, il «se rend compte que le regard de ses quatre ");
		textViewArticleContributeur.setText("toto");
		textViewArticleCategorie.setText("Grand débat");
		textViewArticleRubrique.setText("......");
		textViewArticleMotCle.setText("violence");

		buttonSuppression.setOnClickListener(this);
		spinnerSeletionnerArticle.setOnItemSelectedListener(this);

	}

	private class TacheAsynchroneSelect extends AsyncTask<String, Integer, Map<String, Integer>> {
		@Override
		// ----------------------------
		protected HashMap<String, Integer> doInBackground(String... asParametres) {

			String lsResultat = "";
			
			listArticle = new HashMap<String, Integer>();
			Connection cnx = null;
			Statement lstSQL = null;
			// ma requete préparer pour un select, pour pouvoir remplir le spinner.
			String selectSQL = ("SELECT  id_article,titre_article FROM article");

			try {
				lstSQL =   cnx.createStatement();
	            lrs = lstSQL.executeQuery(selectSQL);
	            while (lrs.next()) {
	            	listArticle.put(lrs.getString(2), lrs.getInt(1));
	            }
	            
	            
				Log.e("requete", selectSQL);

				lsResultat = "l'article est selectionné !";

			} catch (Exception e) {
				System.out.println(e.getMessage());
				lsResultat = e.getMessage();
			}

			return  listArticle;
		} // / doInBackgroundSelect
		
	}
		

		private class TacheAsynchroneDelete extends
				AsyncTask<String, Integer, String> {
			@Override
			// ----------------------------
			protected String doInBackground(String... asParametres) {

				String lsResultat = "";
				int liProgression;

				Connection cnx = null;
				PreparedStatement preparedStatement = null;
				// ma requete préparer pour un delete
				String deleteSQL = "DELETE  FROM journal2014.article WHERE  article.id_article = ?";

				try {
					cnx = DbConnexion.connect();
					preparedStatement = cnx.prepareStatement(deleteSQL);

					preparedStatement.setInt(1, 1001);

					Log.e("requete", deleteSQL);
					// Exécuter supprimer SQL stetement
					preparedStatement.executeUpdate();

					lsResultat = "l'article est supprimé !";

				} catch (Exception e) {

					System.out.println(e.getMessage());
					lsResultat = e.getMessage();
				}

				// Execute la tache en arriere-plan et maj de la barre de
				// progression
				// for (liProgression = 0; liProgression < 100; liProgression++)
				// {
				// try {
				// Thread.sleep(10);
				// } catch (InterruptedException e) {
				// }
				// // Sans l'appel a cette methode l'UI n'est pas maj
				// publishProgress(liProgression);
				// }

				// lsResultat = Integer.toString(liProgression) + " %";
				// Renvoie la valeur a onPostExecute
				return lsResultat;
			} // / doInBackground

			@Override
			// ----------------------------
			protected void onProgressUpdate(Integer... aiProgressions) {

				// Synchronisation avec le thread de l'UI
				// MAJ de la barre de progression
				// barreDeProgression.setProgress(aiProgressions[0]);
				// textViewProgressionPourcentage.setText(Integer.toString(aiProgressions[0])
				// + " %");
			} // / onProgressUpdate

			@Override
			// -------------------------
			protected void onPostExecute(String asResultat) {

				// Synchronisation avec le thread de l'UI
				// Affiche le resultat final
				// barreDeProgression.setProgress(100);
				// textViewProgressionPourcentage.setText(asResultat);

				textViewMessage.setText(asResultat);

			} // / onPostExecute
		} // / TacheAsynchrone
			// / TacheAsynchroneTest

		/*
		 * private static void supprimmerArticle() throws SQLException {
		 * 
		 * Connection cnx = null; PreparedStatement preparedStatement = null; //
		 * ma requete préparer poru un delete String deleteSQL =
		 * "DELETE  FROM journal2014.article article WHERE  article.id_article = idArticle;"
		 * ;
		 * 
		 * try { cnx = DbConnexion.connect(); preparedStatement =
		 * cnx.prepareStatement(deleteSQL); preparedStatement.setInt(1, 1001);
		 * 
		 * //Exécuter supprimer SQL stetement preparedStatement.executeUpdate();
		 * 
		 * System.out.println("L'article est supprimmé!");
		 * 
		 * } catch (Exception e) {
		 * 
		 * System.out.println(e.getMessage());
		 * 
		 * } }
		 */

		public void onClick(View v) {
			// TODO Auto-generated method stub

			// --- Tache asynchrone quand on appuiera sur le boutton supprimer
			if (v == buttonSuppression) {
				new TacheAsynchrone().execute("");
				// // if buttonTacheAsynchrone

				// pour rafraichir le zones de texte.
				editTextNumero.setText("");
				editTextDate.setText("");
				editTextArticleTitre.setText("");
				editTextArticleChapeau.setText("");
				editTextArticleResume.setText("");
				editTextArticleTexte.setText("");
				textViewArticleContributeur.setText("");
				textViewArticleCategorie.setText("");
				textViewArticleRubrique.setText("");
				textViewArticleMotCle.setText("");

			}
		}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}