package org.vazquezj.proyecto.recocido_simulado;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.Random;

public class RecocidoSimulado {
	private final Expression funcionObjetivo;
	private final TipoOptimizacion objetivo;
	private final TipoProblema tipo;
	private final double[] dominio;
	private final double solucionInicial;
	private final int iteracionesPorTemperatura;
	private final double temperaturaInicial;
	private final double temperaturaMinima;
	private final float tasaEnfriamiento;
	private final Random random;

	public RecocidoSimulado(String objetivo, String tipo, double[] dominio, String funcionObjetivo) {
		this.objetivo = TipoOptimizacion.fromString(objetivo);
		this.tipo = TipoProblema.fromString(tipo);
		this.dominio = dominio.clone();
		this.funcionObjetivo = new ExpressionBuilder(funcionObjetivo).variable("x").build();
		this.random = new Random();
		this.solucionInicial = dominio[this.random.nextInt(dominio.length)];
		this.iteracionesPorTemperatura = 5;
		this.temperaturaInicial = 1000;
		this.temperaturaMinima = 1;
		this.tasaEnfriamiento = 0.95f;
	}

	public RecocidoSimulado(String objetivo, String tipo, double[] dominio, String funcionObjetivo, int iteracionesPorTemperatura,
	                        double temperaturaInicial, double temperaturaMinima, float tasaEnfriamiento) {
		this.objetivo = TipoOptimizacion.fromString(objetivo);
		this.tipo = TipoProblema.fromString(tipo);
		this.dominio = dominio.clone();
		this.funcionObjetivo = new ExpressionBuilder(funcionObjetivo).variable("x").build();
		this.random = new Random();
		this.solucionInicial = dominio[random.nextInt(dominio.length)];
		this.iteracionesPorTemperatura = iteracionesPorTemperatura;
		this.temperaturaInicial = temperaturaInicial;
		this.temperaturaMinima = temperaturaMinima;
		this.tasaEnfriamiento = tasaEnfriamiento;
	}

	public double optimizar() {
		double mejorSolucion = solucionInicial;
		double solucionActual = solucionInicial;
		double temperatura = temperaturaInicial;

		while (temperatura > temperaturaMinima) {
			for (int i = 0; i < iteracionesPorTemperatura; i++) {
				double solucionVecina = generarSolucionVecina(solucionActual);

				if (debeAceptarSolucion(solucionActual, solucionVecina, temperatura)) {
					solucionActual = solucionVecina;
				}

				if (esMejorSolucion(solucionActual, mejorSolucion)) {
					mejorSolucion = solucionActual;
				}
			}
			temperatura *= tasaEnfriamiento;
		}
		return mejorSolucion;
	}

	private double generarSolucionVecina(double solucionActual) {
		double delta;
		if (tipo == TipoProblema.DISCRETO) {
			delta = random.nextBoolean() ? 1 : -1;
		} else {
			delta = -1 + (random.nextDouble() * 2);
		}

		double solucionVecina = solucionActual + delta;
		if (solucionVecina < dominio[0]) {
			solucionVecina = (int) dominio[0];
		} else if (solucionVecina > dominio[1]) {
			solucionVecina = (int) dominio[1];
		}
		return solucionVecina;
	}

	private boolean debeAceptarSolucion(double solucionActual, double solucionVecina, double temperatura) {
		double valorActual = evaluarFuncion(solucionActual);
		double valorVecino = evaluarFuncion(solucionVecina);
		double delta = valorVecino - valorActual;

		if (delta <= 0) {
			return true;
		}
		return Math.exp(-delta / temperatura) > random.nextDouble();
	}

	private boolean esMejorSolucion(double solucionActual, double mejorSolucion) {
		double valorActual = evaluarFuncion(solucionActual);
		double valorMejor = evaluarFuncion(mejorSolucion);

		return objetivo == TipoOptimizacion.MINIMIZAR ? valorActual < valorMejor : valorActual > valorMejor;
	}

	private double evaluarFuncion(double x) {
		funcionObjetivo.setVariable("x", x);
		return funcionObjetivo.evaluate();
	}

	private enum TipoOptimizacion {
		MINIMIZAR, MAXIMIZAR;

		public static TipoOptimizacion fromString(String tipo) {
			return "max".equalsIgnoreCase(tipo) ? MAXIMIZAR : MINIMIZAR;
		}
	}

	private enum TipoProblema {
		CONTINUO, DISCRETO;

		public static TipoProblema fromString(String tipo) {
			return "discreto".equalsIgnoreCase(tipo) ? DISCRETO : CONTINUO;
		}
	}
}

