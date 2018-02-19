package libs.java.usecases.cpd;

/**
 * Takes data from input, parse and return
 * 
 * @author Kuldeep
 *
 */
// can also call it Processor
public interface Parser<I, O> {
	public O parse(I value);

}
