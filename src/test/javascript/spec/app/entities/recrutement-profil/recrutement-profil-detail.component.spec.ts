/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CvthequeTestModule } from '../../../test.module';
import { RecrutementProfilDetailComponent } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil-detail.component';
import { RecrutementProfilService } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.service';
import { RecrutementProfil } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.model';

describe('Component Tests', () => {

    describe('RecrutementProfil Management Detail Component', () => {
        let comp: RecrutementProfilDetailComponent;
        let fixture: ComponentFixture<RecrutementProfilDetailComponent>;
        let service: RecrutementProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [RecrutementProfilDetailComponent],
                providers: [
                    RecrutementProfilService
                ]
            })
            .overrideTemplate(RecrutementProfilDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecrutementProfilDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecrutementProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RecrutementProfil(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.recrutementProfil).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
