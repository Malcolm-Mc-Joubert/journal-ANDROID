package fr.m2i.journal2014;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.m2i.journal2014.models.DAOFichier;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class JournalisteForm extends Activity implements OnFocusChangeListener, OnClickListener {

	private EditText editDateInscription;
	private RadioButton radioMadame;
	private RadioButton radioMonsieur;
	private Spinner spinnerStatut;
	private EditText editTextPrenom;
	private EditText editTextNom;
	private EditText editTextEmail;
	private EditText editTextEmailConfirm;
	private EditText editTextPseudo;
	private EditText editTextMotDePasse;
	private EditText editTextMotDePasseConfirm;
	private EditText editTextPhoto;
	private CheckBox checkBoxOffrePartenaire;

	private boolean isNew;

	private Button btValid;
	private Button btDelete;
	private Button btCancel;

	private String pk;

	private DatePickerDialog.OnDateSetListener datePickerDialogListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.journaliste_form);

		initComponents();
		
		initEventListener();
		
		populateStatut();

		// Récupération des paramètres
		Bundle params = this.getIntent().getExtras();
		pk = params.getString("pk");
		isNew = pk.equals("");

		if (isNew) {
			btDelete.setVisibility(View.INVISIBLE);
		}
	}

	private void initComponents() {

		btValid = (Button) findViewById(R.id.buttonJrFormValid);
		btCancel = (Button) findViewById(R.id.buttonJrFormCancel);
		btDelete = (Button) findViewById(R.id.buttonJrFormDelete);

		radioMadame = (RadioButton) findViewById(R.id.radioJournalisteMadame);
		radioMonsieur = (RadioButton) findViewById(R.id.radioJournalisteMonsieur);
		spinnerStatut = (Spinner) findViewById(R.id.spinnerJournalisteStatut);
		editTextPrenom = (EditText) findViewById(R.id.editTextJournalistePrenom);
		editTextNom = (EditText) findViewById(R.id.editTextJournalisteNom);
		editTextEmail = (EditText) findViewById(R.id.editTextJournalisteEmail);
		editTextEmailConfirm = (EditText) findViewById(R.id.editTextJournalisteEmailConfirm);
		editTextPseudo = (EditText) findViewById(R.id.editTextJournalistePseudo);
		editTextMotDePasse = (EditText) findViewById(R.id.editTextJournalisteMotDePasse);
		editTextMotDePasseConfirm = (EditText) findViewById(R.id.editTextJournalisteMotDePasseConfirm);
		editTextPhoto = (EditText) findViewById(R.id.editTextJournalistePhoto);
		checkBoxOffrePartenaire = (CheckBox) findViewById(R.id.CheckJournalisteOffre);
		editDateInscription = (EditText) findViewById(R.id.EditTextJournalisteDateInscription);
		
		

	}

	private void initEventListener() {
		// Écouteurs et gestion d'événements
		
		btCancel.setOnClickListener(this);
		btDelete.setOnClickListener(this);
		btValid.setOnClickListener(this);

		// Gestion du contrôle datePicker
		datePickerDialogListener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker dp, int annee, int mois, int jour) {
				// On affecte à dh les valeurs sélectionnées par l'UT
				Calendar dh = Calendar.getInstance();
				dh.set(Calendar.YEAR, annee);
				dh.set(Calendar.MONTH, mois);
				dh.set(Calendar.DAY_OF_MONTH, jour);

				// --- Formatage de la date (sans l'heure) en fonction de la
				// locale
				DateFormat fmtDate = DateFormat.getDateInstance(
						DateFormat.LONG, Locale.FRANCE);
				// Recupere la date puisque getDateInstance() a ete utilisee
				String lsDate = fmtDate.format(dh.getTime());

				// On affiche la date de naissance formatée dans la zone de
				// saisie
				editDateInscription.setText(lsDate);
			}
		}; // / DatePickerDialog.OnDateSetListener
		
		editDateInscription.setOnFocusChangeListener(this);
		
	}
	
	private void populateStatut(){
		try {
			DAOFichier dao = new DAOFichier(getBaseContext(), R.raw.statut);
			dao.setfirstLineContainsLabel(false);
			dao.loadData();
			
			String statut[] = dao.getColumnAsArray("col2");
			ArrayAdapter<String> aa;
			aa = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,statut);
			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerStatut.setAdapter(aa);
			
		} catch (IOException e) {
			Log.e("Erreur Spinner Statut", e.getMessage());
		} catch (Exception e) {
			Log.e("Erreur Spinner Statut", e.getMessage());
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (v == editDateInscription && hasFocus) {
			// Recuperation de la date actuelle
			Calendar dh = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(this,
					datePickerDialogListener, dh.get(Calendar.YEAR),
					dh.get(Calendar.MONTH), dh.get(Calendar.DAY_OF_MONTH));
			dpd.show();
		}

	}

	@Override
	public void onClick(View v) {
		
		if(v==btCancel){
			
		}
		
		if(v== btValid){
			
		}
		
		if(v==btDelete){
			
		}
		
		finish();
		
	}
}
