
ssh dronic.i3s.unice.fr tar czf byransha.tgz byransha/
scp dronic.i3s.unice.fr:byransha.tgz .
scp byransha.tgz hogie@bastion.i3s.unice.fr:public_html/
echo last version is there: 'https://webusers.i3s.unice.fr/~hogie/byransha.tgz'
