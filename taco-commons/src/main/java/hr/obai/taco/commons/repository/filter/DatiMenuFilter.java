package hr.obai.taco.commons.repository.filter;

public interface DatiMenuFilter {

  StringFilter getCodice();

  void setCodice(StringFilter filter);

  StringFilter getDescrizione();

  void setDescrizione(StringFilter filter);
}
