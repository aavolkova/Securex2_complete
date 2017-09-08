package byAJ.Securex.services;



import byAJ.Securex.models.Role;
import byAJ.Securex.models.Userz;
import byAJ.Securex.repositories.UserzRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService{


    private UserzRepo uRepo;


    public SSUserDetailsService(UserzRepo userRepo){
        this.uRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
        try{
            Userz user = uRepo.findByUsername(username);
            if (user == null){
                System.out.println("user not found with the provided username " + user.toString());
                return null;
            }

            System.out.println(" user from username " + user.toString());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
        } catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
    }




    private Set<GrantedAuthority> getAuthorities (Userz user){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        for(Role role : user.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }

        System.out.println("user authorities are " + authorities.toString());

        return authorities;
    }

}
