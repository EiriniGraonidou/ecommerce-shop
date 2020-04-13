package net.graonidou.assignement.shop.commons;

import java.util.Collection;

import lombok.NoArgsConstructor;

/**
 * Representation of paginated information
 * 
 * @author Eirini Graonidou
 * 
 * @param <T> the type of the contained, paginated entries.
 */
@NoArgsConstructor
public class PageDto<T> {
	
	public int size;
	public int page;
	public long totalElements;
	public Collection<T> content;

}
