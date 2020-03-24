package com.gft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gft.model.StatusTitulo;
import com.gft.model.Titulo;
import com.gft.repository.Titulos;
import com.gft.repository.filter.Titulofilter;



@Service
public class CadastroTituloService {
	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public void excluir(Long codigo) {
		titulos.deleteById(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findById(codigo).get();
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(Titulofilter filtro){
		String descricao = filtro.getDescricao()==null?"":filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
}
