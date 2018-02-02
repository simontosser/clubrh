/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CvthequeTestModule } from '../../../test.module';
import { RecrutementProfilComponent } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.component';
import { RecrutementProfilService } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.service';
import { RecrutementProfil } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.model';

describe('Component Tests', () => {

    describe('RecrutementProfil Management Component', () => {
        let comp: RecrutementProfilComponent;
        let fixture: ComponentFixture<RecrutementProfilComponent>;
        let service: RecrutementProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [RecrutementProfilComponent],
                providers: [
                    RecrutementProfilService
                ]
            })
            .overrideTemplate(RecrutementProfilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecrutementProfilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecrutementProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RecrutementProfil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.recrutementProfils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
