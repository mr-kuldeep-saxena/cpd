package libs.java.usecases.cpd;

/**
 * 
 * @author Kuldeep
 *
 */
// can also call it Processor
public interface Parser <I, O>{
	public O parse (I value);

}
