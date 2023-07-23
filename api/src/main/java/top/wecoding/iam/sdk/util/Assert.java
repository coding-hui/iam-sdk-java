package top.wecoding.iam.sdk.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Liuyuhui
 */
public abstract class Assert {

	/**
	 * Assert a boolean expression, throwing an {@code IllegalArgumentException} if the
	 * expression evaluates to {@code false}.
	 *
	 * <pre class="code">
	 * Assert.isFalse(value, "The value " + value + " must is false");
	 * </pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion {@code false}
	 * @throws IllegalArgumentException if {@code expression} is not {@code false}
	 * @since 0.5
	 */
	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing an {@code IllegalArgumentException} if the
	 * expression evaluates to {@code false}.
	 *
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be null");
	 * </pre>
	 * @param expression a boolean expression
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion {@code
	 *     false}
	 * @throws IllegalArgumentException if the object is not {@code false}
	 * @since 0.5
	 */
	public static <X extends Throwable> void isFalse(boolean expression, Supplier<? extends X> exceptionSupplier)
			throws X {
		if (expression) {
			throw exceptionSupplier.get();
		}
	}

	/**
	 * Assert a boolean expression, throwing an {@code IllegalArgumentException} if the
	 * expression evaluates to {@code false}.
	 *
	 * <pre class="code">
	 * Assert.isFalse(value, "The value {} must be false", value);
	 * </pre>
	 * @param expression a boolean expression
	 * @param errorMsgTemplate an error msg template for the exception to use if the
	 * assertion {@code
	 *     false}
	 * @throws IllegalArgumentException if the expression is not {@code false}
	 * @since 0.5
	 */
	public static void isFalse(boolean expression, String errorMsgTemplate, Object... params)
			throws IllegalArgumentException {
		isFalse(expression, () -> new IllegalArgumentException(Strings.format(errorMsgTemplate, params)));
	}

	/**
	 * Assert a boolean expression, throwing an {@code IllegalArgumentException} if the
	 * expression evaluates to {@code true}.
	 *
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be {@code true}");
	 * </pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion {@code true}
	 * @throws IllegalArgumentException if the object is not {@code true}
	 * @since 0.5
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an object is{@code true}.
	 *
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be {@code true}");
	 * </pre>
	 * @param expression a boolean expression
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion {@code
	 *     true}
	 * @throws IllegalArgumentException if the object is not {@code true}
	 * @since 0.5
	 */
	public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> exceptionSupplier)
			throws X {
		if (!expression) {
			throw exceptionSupplier.get();
		}
	}

	/**
	 * Assert that an object is {@code true}.
	 *
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be {@code true}");
	 * </pre>
	 * @param expression a boolean expression
	 * @param errorMsgTemplate an exception msg template for the exception to use if the
	 * assertion {@code true}
	 * @throws IllegalArgumentException if the object is not {@code true}
	 * @since 0.5
	 */
	public static void isTrue(boolean expression, String errorMsgTemplate, Object... params)
			throws IllegalArgumentException {
		isTrue(expression, () -> new IllegalArgumentException(Strings.format(errorMsgTemplate, params)));
	}

	/**
	 * Assert that an object is {@code null}.
	 *
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be null");
	 * </pre>
	 * @param object the object to check
	 * @param message the exception message to use if not {@code true}
	 * @throws IllegalArgumentException if the object is not {@code null}
	 * @since 0.5
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an object is {@code null}.
	 *
	 * <pre class="code">
	 * Assert.isNull(value, () -&gt; "The value '" + value + "' must be null");
	 * </pre>
	 * @param object the object to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object is not {@code null}
	 * @since 0.5
	 */
	public static <X extends Throwable> void isNull(Object object, Supplier<? extends X> exceptionSupplier) {
		if (object != null) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that an object is not {@code null}.
	 *
	 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
	 * @param object the object to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the object is {@code null}
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an object is not {@code null}.
	 *
	 * <pre class="code">
	 * Assert.notNull(entity.getId(),
	 *     () -&gt; "ID for entity " + entity.getName() + " must not be null");
	 * </pre>
	 * @param object the object to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object is {@code null}
	 * @since 0.5
	 */
	public static <X extends Throwable> void notNull(Object object, Supplier<? extends X> exceptionSupplier) {
		if (object == null) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that a str is not {@code blank}.
	 *
	 * <pre class="code">Assert.notNull(clazz, "The class must not be blank");</pre>
	 * @param str the str to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the str is {@code blank}
	 */
	public static void notBlank(String str, String message) {
		if (Strings.isBlank(str)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that a str is not {@code blank}.
	 *
	 * <pre class="code">
	 * Assert.notNull(entity.getId(),
	 *     () -&gt; "ID for entity " + entity.getName() + " must not be blank");
	 * </pre>
	 * @param str the str to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the str is {@code blank}
	 * @since 0.5
	 */
	public static <X extends Throwable> void notBlank(String str, Supplier<? extends X> exceptionSupplier) {
		if (Strings.isBlank(str)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that the given String is not empty; that is, it must not be {@code null} and
	 * not the empty String.
	 *
	 * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the text is empty
	 * @see Strings#hasLength
	 */
	public static void hasLength(String text, String message) {
		if (!Strings.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that the given String is not empty; that is, it must not be {@code null} and
	 * not the empty String.
	 *
	 * <pre class="code">
	 * Assert.hasLength(account.getName(),
	 *     () -&gt; "Name for account '" + account.getId() + "' must not be empty");
	 * </pre>
	 * @param text the String to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the text is empty
	 * @since 0.5
	 * @see Strings#hasLength
	 */
	public static <X extends Throwable> void hasLength(String text, Supplier<? extends X> exceptionSupplier) {
		if (!Strings.hasLength(text)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that the given String contains valid text content; that is, it must not be
	 * {@code null} and must contain at least one non-whitespace character.
	 *
	 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the text does not contain valid text content
	 * @see Strings#hasText
	 */
	public static void hasText(String text, String message) {
		if (!Strings.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that the given String contains valid text content; that is, it must not be
	 * {@code null} and must contain at least one non-whitespace character.
	 *
	 * <pre class="code">
	 * Assert.hasText(account.getName(),
	 *     () -&gt; "Name for account '" + account.getId() + "' must not be empty");
	 * </pre>
	 * @param text the String to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the text does not contain valid text content
	 * @since 0.5
	 * @see Strings#hasText
	 */
	public static <X extends Throwable> void hasText(String text, Supplier<? extends X> exceptionSupplier) {
		if (!Strings.hasText(text)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 *
	 * <pre class=
	 * "code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the text contains the substring
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (Strings.hasLength(textToSearch) && Strings.hasLength(substring) && textToSearch.contains(substring)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 *
	 * <pre class="code">
	 * Assert.doesNotContain(name, forbidden, () -&gt; "Name must not contain '" + forbidden + "'");
	 * </pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the text contains the substring
	 * @since 0.5
	 */
	public static <X extends Throwable> void doesNotContain(String textToSearch, String substring,
			Supplier<? extends X> exceptionSupplier) {
		if (Strings.hasLength(textToSearch) && Strings.hasLength(substring) && textToSearch.contains(substring)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that an array contains elements; that is, it must not be {@code null} and
	 * must contain at least one element.
	 *
	 * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
	 * @param array the array to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the object array is {@code null} or contains no
	 * elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (Objects.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an array contains elements; that is, it must not be {@code null} and
	 * must contain at least one element.
	 *
	 * <pre class="code">
	 * Assert.notEmpty(array, () -&gt; "The " + arrayType + " array must contain elements");
	 * </pre>
	 * @param array the array to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object array is {@code null} or contains no
	 * elements
	 * @since 0.5
	 */
	public static <X extends Throwable> void notEmpty(Object[] array, Supplier<? extends X> exceptionSupplier) {
		if (Objects.isEmpty(array)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that an array contains elements; that is, it must not be {@code null} and
	 * must contain at least one element.
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Object[], String)}
	 */
	@Deprecated
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that an array contains no {@code null} elements.
	 *
	 * <p>
	 * Note: Does not complain if the array is empty!
	 *
	 * <pre class=
	 * "code">Assert.noNullElements(array, "The array must contain non-null elements");
	 * </pre>
	 * @param array the array to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the object array contains a {@code null}
	 * element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	/**
	 * Assert that an array contains no {@code null} elements.
	 *
	 * <p>
	 * Note: Does not complain if the array is empty!
	 *
	 * <pre class="code">
	 * Assert.noNullElements(array, () -&gt; "The " + arrayType + " array must contain non-null elements");
	 * </pre>
	 * @param array the array to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the object array contains a {@code null}
	 * element
	 * @since 0.5
	 */
	public static <X extends Throwable> void noNullElements(Object[] array, Supplier<? extends X> exceptionSupplier) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
				}
			}
		}
	}

	/**
	 * Assert that an array contains no {@code null} elements.
	 * @deprecated as of 4.3.7, in favor of {@link #noNullElements(Object[], String)}
	 */
	@Deprecated
	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	/**
	 * Assert that a collection contains elements; that is, it must not be {@code null}
	 * and must contain at least one element.
	 *
	 * <pre class=
	 * "code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
	 * @param collection the collection to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the collection is {@code null} or contains no
	 * elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (Collections.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that a collection contains elements; that is, it must not be {@code null}
	 * and must contain at least one element.
	 *
	 * <pre class="code">
	 * Assert.notEmpty(collection, () -&gt; "The " + collectionType + " collection must contain elements");
	 * </pre>
	 * @param collection the collection to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the collection is {@code null} or contains no
	 * elements
	 * @since 0.5
	 */
	public static <X extends Throwable> void notEmpty(Collection<?> collection,
			Supplier<? extends X> exceptionSupplier) {
		if (Collections.isEmpty(collection)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that a collection contains elements; that is, it must not be {@code null}
	 * and must contain at least one element.
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Collection, String)}
	 */
	@Deprecated
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that a collection contains no {@code null} elements.
	 *
	 * <p>
	 * Note: Does not complain if the collection is empty!
	 *
	 * <pre class="code">
	 * Assert.noNullElements(collection, "Collection must contain non-null elements");</pre>
	 * @param collection the collection to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the collection contains a {@code null} element
	 * @since 5.2
	 */
	public static void noNullElements(Collection<?> collection, String message) {
		if (collection != null) {
			for (Object element : collection) {
				if (element == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	/**
	 * Assert that a collection contains no {@code null} elements.
	 *
	 * <p>
	 * Note: Does not complain if the collection is empty!
	 *
	 * <pre class="code">
	 * Assert.noNullElements(collection, () -&gt; "Collection " + collectionName + " must contain non-null elements");
	 * </pre>
	 * @param collection the collection to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the collection contains a {@code null} element
	 * @since 5.2
	 */
	public static <X extends Throwable> void noNullElements(Collection<?> collection,
			Supplier<? extends X> exceptionSupplier) {
		if (collection != null) {
			for (Object element : collection) {
				if (element == null) {
					throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
				}
			}
		}
	}

	/**
	 * Assert that a Map contains entries; that is, it must not be {@code null} and must
	 * contain at least one entry.
	 *
	 * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
	 * @param map the map to check
	 * @param message the exception to use if the assertion fails
	 * @throws IllegalArgumentException if the map is {@code null} or contains no entries
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (Collections.isEmpty(map)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that a Map contains entries; that is, it must not be {@code null} and must
	 * contain at least one entry.
	 *
	 * <pre class="code">
	 * Assert.notEmpty(map, () -&gt; "The " + mapType + " map must contain entries");
	 * </pre>
	 * @param map the map to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails
	 * @throws IllegalArgumentException if the map is {@code null} or contains no entries
	 * @since 0.5
	 */
	public static <X extends Throwable> void notEmpty(Map<?, ?> map, Supplier<? extends X> exceptionSupplier) {
		if (Collections.isEmpty(map)) {
			throw new IllegalArgumentException(nullSafeGet(exceptionSupplier));
		}
	}

	/**
	 * Assert that a Map contains entries; that is, it must not be {@code null} and must
	 * contain at least one entry.
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Map, String)}
	 */
	@Deprecated
	public static void notEmpty(Map<?, ?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 *
	 * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param message a message which will be prepended to provide further context. If it
	 * is empty or ends in ":" or ";" or "," or ".", a full exception will be appended. If
	 * it ends in a space, the name of the offending object's type will be appended. In
	 * any other case, a ":" with a space and the name of the offending object's type will
	 * be appended.
	 * @throws IllegalArgumentException if the object is not an instance of type
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			instanceCheckFailed(type, obj, message);
		}
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 *
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo, () -&gt; "Processing " + Foo.class.getSimpleName() + ":");
	 * </pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails. See {@link #isInstanceOf(Class, Object, String)} for details.
	 * @throws IllegalArgumentException if the object is not an instance of type
	 * @since 0.5
	 */
	public static void isInstanceOf(Class<?> type, Object obj, Supplier<String> exceptionSupplier) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			instanceCheckFailed(type, obj, nullSafeGetStr(exceptionSupplier));
		}
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 *
	 * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @throws IllegalArgumentException if the object is not an instance of type
	 */
	public static void isInstanceOf(Class<?> type, Object obj) {
		isInstanceOf(type, obj, "");
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 *
	 * <pre class=
	 * "code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
	 * @param superType the supertype to check against
	 * @param subType the subtype to check
	 * @param message a message which will be prepended to provide further context. If it
	 * is empty or ends in ":" or ";" or "," or ".", a full exception will be appended. If
	 * it ends in a space, the name of the offending subtype will be appended. In any
	 * other case, a ":" with a space and the name of the offending subtype will be
	 * appended.
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Supertype to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			assignableCheckFailed(superType, subType, message);
		}
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 *
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass, () -&gt; "Processing " + myAttributeName + ":");
	 * </pre>
	 * @param superType the supertype to check against
	 * @param subType the subtype to check
	 * @param exceptionSupplier a exceptionSupplier for the exception to use if the
	 * assertion fails. See {@link #isAssignable(Class, Class, String)} for details.
	 * @throws IllegalArgumentException if the classes are not assignable
	 * @since 0.5
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, Supplier<String> exceptionSupplier) {
		notNull(superType, "Supertype to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			assignableCheckFailed(superType, subType, nullSafeGetStr(exceptionSupplier));
		}
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 *
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the supertype to check
	 * @param subType the subtype to check
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
		String className = (obj != null ? obj.getClass().getName() : "null");
		String result = "";
		boolean defaultMessage = true;
		if (Strings.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, className);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + ("Object of class [" + className + "] must be an instance of " + type);
		}
		throw new IllegalArgumentException(result);
	}

	private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
		String result = "";
		boolean defaultMessage = true;
		if (Strings.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, subType);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + (subType + " is not assignable to " + superType);
		}
		throw new IllegalArgumentException(result);
	}

	private static boolean endsWithSeparator(String msg) {
		return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
	}

	private static String messageWithTypeName(String msg, Object typeName) {
		return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
	}

	private static <X extends Throwable> Throwable nullSafeGet(Supplier<? extends X> exceptionSupplier) {
		return (exceptionSupplier != null ? exceptionSupplier.get() : null);
	}

	private static String nullSafeGetStr(Supplier<String> messageSupplier) {
		return (messageSupplier != null ? messageSupplier.get() : null);
	}

}
