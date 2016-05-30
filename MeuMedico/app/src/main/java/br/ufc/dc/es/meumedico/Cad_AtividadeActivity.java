package br.ufc.dc.es.meumedico;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufc.dc.es.dao.AtividadeDAO;
import br.ufc.dc.es.model.Atividade;

/**
 * Created by César on 29/05/2016.
 */
public class Cad_AtividadeActivity extends Activity{

    Button atividade;
    AtividadeHelper helper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_atividade);
        callCriarAtividade();
    }

    public void callCriarAtividade(){

        atividade = (Button) findViewById(R.id.AtSalvar);
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper = new AtividadeHelper(Cad_AtividadeActivity.this);
                AtividadeDAO dao = new AtividadeDAO(Cad_AtividadeActivity.this);

                Atividade atividade = helper.pegaCamposAtividade();
                id = getIntent().getIntExtra("id", 0);
                atividade.setId_usuario(id);
                dao.insert(atividade);
                dao.close();
                Toast.makeText(Cad_AtividadeActivity.this, "Atividade inserida com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}