package mx.com.nmp.escenariosdinamicos.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Repository;
@Repository
public class FechasComunes {
	public  Date resetTimeToUp(Date fecha) {//La fecha actual la convierte hasta el ultimo minuto del dia
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public  Date resetTimeToDown(Date fecha,Integer diferenciaDias) {//La fecha actual la convierte hasta la primera hora del dia
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, diferenciaDias);
		return cal.getTime();
	}
}
