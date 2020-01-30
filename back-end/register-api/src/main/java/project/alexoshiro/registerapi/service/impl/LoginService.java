package project.alexoshiro.registerapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.alexoshiro.registerapi.model.SystemUser;
import project.alexoshiro.registerapi.repository.UserRepository;

@Service
public class LoginService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		List<SystemUser> user = userRepository.findByUsername(username);
		if (user != null && !user.isEmpty()) {
			return new User(user.get(0).getUsername(), user.get(0).getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
	}

}
