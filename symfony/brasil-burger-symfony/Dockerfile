FROM php:8.2-apache

# Installer ce dont Symfony a besoin
RUN apt-get update && apt-get install -y \
    libpq-dev libzip-dev libonig-dev libicu-dev \
    zip unzip git curl \
    && docker-php-ext-install \
        pdo pdo_pgsql zip mbstring intl bcmath \
    && a2enmod rewrite \
    && rm -rf /var/lib/apt/lists/*

# Copier le code
COPY . /var/www/html/

# Se placer dans le dossier
WORKDIR /var/www/html

# Démarrer Apache
CMD ["apache2-foreground"]