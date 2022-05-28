package woowacourse.auth.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Member {

	@Include
	private Long id;
	private final String email;
	private final String password;

	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
