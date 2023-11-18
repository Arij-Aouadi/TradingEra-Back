package tn.esprit.shadowtradergo.Services.Classes;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Theme;
import tn.esprit.shadowtradergo.DAO.Repositories.ThemeRepository;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Theme getThemeById(Long themeId) {
        return themeRepository.findById(themeId).orElse(null);
    }

    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }
}
