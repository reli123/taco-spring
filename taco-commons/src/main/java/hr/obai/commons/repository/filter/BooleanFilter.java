package hr.obai.commons.repository.filter;

/**
 * Class for filtering attributes with {@link Boolean} type. It can be added to a criteria class as
 * a member, to support the following query parameters:
 *
 * <pre>
 *      fieldName.equals=true
 * </pre>
 */
public class BooleanFilter extends Filter<Boolean> {}
