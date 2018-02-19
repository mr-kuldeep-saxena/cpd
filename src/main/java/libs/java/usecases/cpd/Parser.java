package libs.java.usecases.cpd;

/**
 * Takes data from input, parse and put on O
 * 
 * @author Kuldeep
 *
 */
// can also call it Processor
public interface Parser<I, O> {
	public O parse(I value);

}
